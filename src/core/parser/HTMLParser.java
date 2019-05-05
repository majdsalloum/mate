package core.parser;

import core.exceptions.CommentWithoutEndException;
import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import core.exceptions.MoreThanOneRootException;
import core.tags.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HTMLParser {

    public static class TagLocation {
        Integer startTagBegin;
        Integer startTagEnd;
        Integer endTagBegin;
        Integer endTagEnd;
        Tag tag;
        TagLocation father;
        String tagText = null;
        String originalTag = null;

        public boolean includes(TagLocation tagLocation) {
            return this.startTagEnd < tagLocation.startTagBegin &&
                    this.endTagBegin > tagLocation.endTagEnd;
        }

    }

    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ");
    }

    private static String removeComments(String text) throws CommentWithoutEndException {
        String replacedValidComments = text.replaceAll("<!--(?:.|\\s)*?-->", "");
        if (replacedValidComments.contains("<!--"))
            throw new CommentWithoutEndException("At index" + replacedValidComments.indexOf("<!--"));
        return replacedValidComments;
    }

    private static Integer min(Integer... list) {
        if (list.length == 2)
            return list[0] != null && list[1] != null ? Math.min(list[0], list[1]) : list[0] != null ? list[0] : list[1];
        Integer min = null;
        for (Integer item : list)
            if (item != null)
                min = min(item, min);
        return min;
    }

    private static LinkedList<TagLocation> detectTagsLocations(String text) throws InvalidSyntaxException {//todo : make it return tag location list
        final Pattern openTag = Pattern.compile("<(\\w+)\\s?(?:\\w+(?:=(['\"])(?:.|\\s)+?\\2)?\\s?)*?\\s*?>");
        final Pattern closeTag = Pattern.compile("</(\\w+)\\s*>");
        final Pattern openAndCloseTag = Pattern.compile("<(\\w+)\\s?(?:\\w+=(['\"])(?:.|\\s)+?\\2\\s?)*\\s*?/>");
        LinkedList<TagLocation> tagLocationList = new LinkedList<>();
        LinkedList<TagLocation> tempStack = new LinkedList<>();
        // TODO WE CAN MAKE THIS FASTER BY MAKING mass find for each tag type and include the step of finding father here
        int start = 0;
        while (true) {
            int newEnd;
            Matcher openTagMatcher = openTag.matcher(text);
            Matcher closeTagMatcher = closeTag.matcher(text);
            Matcher openAndCloseMatcher = openAndCloseTag.matcher(text);
            Integer openTagStart = null, closeTagStart = null, openAndCloseTagStart = null;
            if (openTagMatcher.find(start))
                openTagStart = openTagMatcher.start();
            if (closeTagMatcher.find(start))
                closeTagStart = closeTagMatcher.start();
            if (openAndCloseMatcher.find(start))
                openAndCloseTagStart = openAndCloseMatcher.start();
            Integer minStart = min(openTagStart, closeTagStart, openAndCloseTagStart);
            if (minStart == null)
                break;
            TagLocation tagLocationToAdd = null;
            if (minStart.equals(openTagStart)) {
                TagLocation tagLocation = new TagLocation();
                tagLocation.startTagBegin = openTagMatcher.start();
                tagLocation.startTagEnd = openTagMatcher.end() - 1;
                tagLocation.tagText = openTagMatcher.group(1).toLowerCase();
                Tag tag;
                try {
                    tag = (Tag) Class.forName("core.tags." + openTagMatcher.group(1).toUpperCase()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    tag = new DIV();
                    tagLocation.originalTag = openTagMatcher.group(1);
                }
                tagLocation.tag = tag;
                tagLocationToAdd = tagLocation;
                tempStack.push(tagLocation);
                newEnd = openTagMatcher.end();
            } else if (minStart.equals(closeTagStart)) {
                if (!closeTagMatcher.group(1).toLowerCase().equals(tempStack.getFirst().tagText)
                        &&
                        tempStack.getFirst().tag.requiresClosing()
                )
                    throw new InvalidSyntaxException(tempStack.getFirst().tag.toString() + " not closed correctly");
                else {
                    TagLocation tagLocation = tempStack.pop();
                    tagLocation.endTagBegin = closeTagMatcher.start();
                    tagLocation.endTagEnd = closeTagMatcher.end() - 1;
                }
                newEnd = closeTagMatcher.end();
            } else {
                Tag tag;
                TagLocation tagLocation = new TagLocation();
                try {
                    tag = (Tag) Class.forName("core.tags." + openAndCloseMatcher.group(1).toUpperCase()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    tag = new DIV();
                    tagLocation.originalTag = openAndCloseMatcher.group(1);
                }
                tagLocation.startTagBegin = openAndCloseMatcher.start();
                tagLocation.endTagBegin = openAndCloseMatcher.start();
                tagLocation.startTagEnd = openAndCloseMatcher.end() - 1;
                tagLocation.endTagEnd = openAndCloseMatcher.end() - 1;
                tagLocation.tag = tag;
                tagLocationToAdd = tagLocation;
                newEnd = openAndCloseMatcher.end();
            }
            try {
                String remainingText = text.substring(start, minStart).trim();
                if (!remainingText.isEmpty()) {
                    TagLocation textTagLocation = new TagLocation();
                    textTagLocation.startTagBegin = start;
                    textTagLocation.startTagEnd = minStart - 1;
                    textTagLocation.endTagBegin = start;
                    textTagLocation.endTagEnd = minStart - 1;
                    textTagLocation.tagText = remainingText;
                    if (tagLocationToAdd != null)
                        tagLocationList.add(tagLocationList.size() - 1, textTagLocation);
                    else
                        tagLocationList.add(textTagLocation);
                }
            } catch (Exception ignored) {
            }
            if (tagLocationToAdd != null)
                tagLocationList.add(tagLocationToAdd);
            start = newEnd;
        }
        return tagLocationList;
    }


    private static void setAttributes(LinkedList<TagLocation> list, String text) throws InvalidContentException {
        final Pattern attributePattern = Pattern.compile("(\\w+)(?:=(['\"])((?:.|\\s)+?)\\2)?(\\s|>|/>)");
        for (TagLocation tagLocation : list) {
            if (tagLocation.tag == null) continue;
            String tagStartText = text.substring(tagLocation.startTagBegin + (tagLocation.tagText != null ? tagLocation.tagText.length() + 1 : 0), tagLocation.startTagEnd + 1);
            final Matcher matcher = attributePattern.matcher(tagStartText);
            HashMap<String, Object> attributes = new HashMap<>();
            while (matcher.find()) {
                if (matcher.groupCount() == 1)
                    attributes.put(matcher.group(1), true);
                else
                    attributes.put(matcher.group(1), matcher.group(3));
            }
            tagLocation.tag.setAttributes(attributes);
        }
    }

    private static void getTree(LinkedList<TagLocation> list) throws InvalidContentException, MoreThanOneRootException {
        LinkedList<TagLocation> stack = new LinkedList<>();
        stack.push(list.getFirst());
        for (TagLocation tagLocation : list) {
            if (tagLocation == stack.peek()) continue; // first time
            if (stack.isEmpty())
                throw new MoreThanOneRootException("");
            while (!stack.peek().includes(tagLocation)) {
                stack.poll();
                if (stack.isEmpty())
                    throw new MoreThanOneRootException("");
            }
            TagLocation top = stack.peek();
            tagLocation.father = top;
            if (tagLocation.tag != null) {
                top.tag.addChildren(tagLocation.tag);
                stack.push(tagLocation);
            } else
                top.tag.addChildren(tagLocation.tagText);
        }
    }


    public static Tag compile(String text) {
        try {
            String textWithoutComments = removeComments(text);
            LinkedList<TagLocation> tagsLocations = detectTagsLocations(textWithoutComments);
            getTree(tagsLocations);
            setAttributes(tagsLocations, textWithoutComments);
            return tagsLocations.getFirst().tag;
        } catch (InvalidSyntaxException | InvalidContentException e) {
            e.printStackTrace();
            return null;
        }
    }

}
