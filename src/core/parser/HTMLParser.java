package core.parser;

import core.exceptions.CommentWithoutEndException;
import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import core.exceptions.MoreThanOneRootException;
import core.tags.*;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.css.Match;
import tests.ParsingTest;


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
            if (endTagBegin == null)
                return false;
            else if (tagLocation.endTagBegin == null)
                return tagLocation.startTagBegin > startTagEnd && tagLocation.startTagEnd < endTagBegin;
            return this.startTagEnd < tagLocation.startTagBegin &&
                    this.endTagBegin > tagLocation.endTagEnd;
        }

        @Override
        public String toString() {
            if (originalTag != null)
                return originalTag;
            if (tag != null)
                return tag.toString();
            else
                return tagText;
        }
    }


    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ");
    }

    private static String removeComments(String text) throws CommentWithoutEndException {

        String replacedValidComments = text.replaceAll("<!--(?:.|\\s)*?-->", "");
        if (replacedValidComments.contains("<!--"))
            throw new CommentWithoutEndException("At index" + replacedValidComments.indexOf("<!--"));
        return replacedValidComments.replaceAll("<!DOCTYPE html>", "");
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

    private static class MatchSummary implements Comparable<MatchSummary> {
        Integer start;
        Integer end;
        String[] groups;

        public MatchSummary(int start, int end, String[] groups) {
            this.start = start;
            this.end = end;
            this.groups = groups;
        }

        public MatchSummary(int start) {
            this.start = start;
        }

        @Override
        public int compareTo(MatchSummary o) {
            return start.compareTo(o.start);
        }
    }

    private static MatchSummary matcherToSummary(Matcher matcher) {
        String[] groups = new String[matcher.groupCount() + 1];
        for (int i = 0; i < groups.length; i++)
            groups[i] = matcher.group(i);
        return new MatchSummary(matcher.start(), matcher.end(), groups);
    }

//    private static void convertSymbols(String text)
//    {
//        try {
//            text = java.net.URLDecoder.decode(text,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            text=text;
//        }
//    }
    private static Integer closestSummaryIndex(LinkedList<MatchSummary> summaries, int start) {
        Integer idx = Arrays.binarySearch(summaries.toArray(), new MatchSummary(start));
        if (idx < 0) idx = -1 * (idx + 1);
        if (idx + 1 > summaries.size() || summaries.isEmpty())
            idx = null;
        return idx;
    }

    private static LinkedList<TagLocation> detectTagsLocations(String text) throws InvalidSyntaxException {

        final Pattern openTag = Pattern.compile("<(!?\\w+)(?:\\s(?:\\w+(?:=(['\"]?).+?\\2)?\\s?)*?)?\\s*>");
        final Pattern closeTag = Pattern.compile("</(\\w+)\\s*>");
        final Pattern openAndCloseTag = Pattern.compile("<(!?\\w+)(?:\\s(?:\\w+(?:=(['\"]?).+?\\2)?\\s?)*?)?\\s*/>");
        LinkedList<TagLocation> tagLocationList = new LinkedList<>();
        LinkedList<TagLocation> tempStack = new LinkedList<>();
        LinkedList<MatchSummary> openTagSummaries = new LinkedList<>();
        LinkedList<MatchSummary> closeTagSummaries = new LinkedList<>();
        LinkedList<MatchSummary> openAndCloseTagSummaries = new LinkedList<>();
        ParsingTest.log("i am in get tag location");
        {
            Matcher openTagMatcher = openTag.matcher(text);
            Matcher closeTagMatcher = closeTag.matcher(text);
            Matcher openAndCloseMatcher = openAndCloseTag.matcher(text);
            // TODO WE CAN MAKE THIS FASTER Including the step of finding father here

            while (openTagMatcher.find())
                openTagSummaries.add(matcherToSummary(openTagMatcher));
            while (closeTagMatcher.find())
                closeTagSummaries.add(matcherToSummary(closeTagMatcher));
            while (openAndCloseMatcher.find())
                openAndCloseTagSummaries.add(matcherToSummary(openAndCloseMatcher));
        }
        int start = 0;
        boolean usingHtml5 = false;
        while (true) {
            int newEnd;
            Integer openTagStart = closestSummaryIndex(openTagSummaries, start);
            Integer closeTagStart = closestSummaryIndex(closeTagSummaries, start);
            Integer openAndCloseTagStart = closestSummaryIndex(openAndCloseTagSummaries, start);
            Integer minStart = min(
                    openTagStart == null ? null : openTagSummaries.get(openTagStart).start,
                    closeTagStart == null ? null : closeTagSummaries.get(closeTagStart).start,
                    openAndCloseTagStart == null ? null : openAndCloseTagSummaries.get(openAndCloseTagStart).start
            );
            if (minStart == null)
                break;
            MatchSummary matchSummary;
            TagLocation tagLocationToAdd = null;
            if (openTagStart != null && minStart.equals(openTagSummaries.get(openTagStart).start)) {
                matchSummary = openTagSummaries.get(openTagStart);
                ParsingTest.log(matchSummary.groups[0]);
                TagLocation tagLocation = new TagLocation();
                tagLocation.startTagBegin = matchSummary.start;
                tagLocation.startTagEnd = matchSummary.end - 1;
                tagLocation.tagText = matchSummary.groups[1].toLowerCase();
                Tag tag;
                try {
                    tag = (Tag) Class.forName("core.tags." + matchSummary.groups[1].toUpperCase()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    tag = new DIV();
                    tagLocation.originalTag = matchSummary.groups[1];
                }
                tagLocation.tag = tag;
                if (tagLocation.originalTag == null || tagLocation.originalTag.charAt(0) != '!') {
                    tagLocationToAdd = tagLocation;
                    tempStack.push(tagLocation);
                } else {
                    usingHtml5 = true;
                }
                newEnd = matchSummary.end;
            } else if (closeTagStart != null && minStart.equals(closeTagSummaries.get(closeTagStart).start)) {
                matchSummary = closeTagSummaries.get(closeTagStart);
                ParsingTest.log(matchSummary.groups[0]);
                while (!tempStack.peek().tag.requiresClosing() &&
                        !tempStack.peek().tag.toString().toLowerCase().equals(matchSummary.groups[1].toLowerCase()))
                    tempStack.pop();
                if (!matchSummary.groups[1].toLowerCase().equals(tempStack.getFirst().tagText)
                        &&
                        tempStack.getFirst().tag.requiresClosing()
                        )
                    throw new InvalidSyntaxException(tempStack.getFirst().tag.toString() + " not closed correctly");
                else {
                    TagLocation tagLocation = tempStack.pop();
                    tagLocation.endTagBegin = matchSummary.start;
                    tagLocation.endTagEnd = matchSummary.end - 1;
                }
                newEnd = matchSummary.end;
            } else {
                matchSummary = openAndCloseTagSummaries.get(openAndCloseTagStart);
                ParsingTest.log(matchSummary.groups[0]);
                Tag tag;
                TagLocation tagLocation = new TagLocation();
                try {
                    tag = (Tag) Class.forName("core.tags." + matchSummary.groups[1].toUpperCase()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    tag = new DIV();
                    tagLocation.originalTag = matchSummary.groups[1];
                }
                tagLocation.startTagBegin = matchSummary.start;
                tagLocation.endTagBegin = matchSummary.start;
                tagLocation.startTagEnd = matchSummary.end - 1;
                tagLocation.endTagEnd = matchSummary.end - 1;
                tagLocation.tag = tag;
                tagLocationToAdd = tagLocation;
                newEnd = matchSummary.end;
            }
            try {
                String remainingText = text.substring(start, minStart).trim();
//                convertSymbols(remainingText);
                if (!remainingText.isEmpty()) {
                    TagLocation textTagLocation = new TagLocation();
                    textTagLocation.startTagBegin = start;
                    textTagLocation.startTagEnd = minStart - 1;
                    textTagLocation.endTagBegin = start;
                    textTagLocation.endTagEnd = minStart - 1;
                    textTagLocation.tagText = remainingText;
//                    if (tagLocationToAdd != null)
//                        tagLocationList.add(tagLocationList.size() - 1, textTagLocation);
//                    else
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

    // private setAttributes(LinkedList<TagLocation list> , String)
    private static void setAttributes(LinkedList<TagLocation> list, String text) throws InvalidContentException {
        final Pattern attributePattern = Pattern.compile("(\\w+)(?:=(['\"]?)((?:.|\\s)+?)\\2)?(\\s|>|/>)");
        for (TagLocation tagLocation : list) {
            if (tagLocation.tag == null) continue;
            String tagStartText = text.substring(tagLocation.startTagBegin + (tagLocation.tagText != null ? tagLocation.tagText.length() + 1 : 0), tagLocation.startTagEnd + 1);
            final Matcher matcher = attributePattern.matcher(tagStartText);
            HashMap<String, Object> attributes = new HashMap<>();
            while (matcher.find()) {
                ParsingTest.log("i am in set attr");

                if (matcher.groupCount() == 1)
                    attributes.put(matcher.group(1), true);
                else
                    attributes.put(matcher.group(1), matcher.group(3));
            }
            tagLocation.tag.setAttributes(attributes);
        }
    }

    private static void getTree(LinkedList<TagLocation> list) throws InvalidContentException {
        LinkedList<TagLocation> stack = new LinkedList<>();
        stack.push(list.getFirst());
        for (TagLocation tagLocation : list) {
            if (tagLocation == stack.peek()) continue; // first time
            if (stack.isEmpty())
                throw new MoreThanOneRootException("");
            while (!stack.peek().includes(tagLocation)) {
                ParsingTest.log("i am in get tree");
                stack.poll();
                if (stack.isEmpty())
                    throw new MoreThanOneRootException("");
            }
            TagLocation top = stack.peek();
            tagLocation.father = top;
            if (tagLocation.tag != null) {
                top.tag.addChildren(tagLocation.tag);
                if (tagLocation.endTagBegin != null)
                    stack.push(tagLocation);
            } else
                top.tag.addChildren(tagLocation.tagText);
        }
    }


    public static Tag compile(String text) throws InvalidSyntaxException, InvalidContentException {
        String textWithoutComments = removeComments(text);
        LinkedList<TagLocation> tagsLocations = detectTagsLocations(textWithoutComments);
        getTree(tagsLocations);
        setAttributes(tagsLocations, textWithoutComments);
        return tagsLocations.getFirst().tag;

    }


}