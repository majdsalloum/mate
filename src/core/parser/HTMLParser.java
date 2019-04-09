package core.parser;

import core.exceptions.CommentWithoutEndException;
import core.exceptions.InvalidContentException;
import core.exceptions.UnsupportedChildrenTag;
import core.tags.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HTMLParser {

    private static class TagLocation {
        Integer startTagBegin;
        Integer startTagEnd;
        Integer endTagBegin;
        Integer endTagEnd;
        Tag tag;
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

    private static TagLocation[] detectTagsLocations(String text) {
        final Pattern openTag = Pattern.compile("");
        final Pattern closeTag = Pattern.compile("");
        final Pattern openAndCloseTag = Pattern.compile("");
        LinkedList<TagLocation> tagLocationList = new LinkedList<>();
        int start = 0;
        while (true) {
            Matcher openTagMatcher = openTag.matcher(text);
            Matcher closeTagMatcher = closeTag.matcher(text);
            Matcher openAndCloseMatcher = openAndCloseTag.matcher(text);
            Integer openTagStart = null, closeTagStart = null, openAndCloseTagStart = null;
            if (openTagMatcher.find(start))
                openTagStart = openTagMatcher.start();
            if (closeTagMatcher.find(start))
                closeTagStart = openTagMatcher.start();
            if (openAndCloseMatcher.find(start))
                openAndCloseTagStart = openTagMatcher.start();
            Integer minStart = min(openTagStart, closeTagStart, openAndCloseTagStart);
            if (minStart == null)
                break;
            if (minStart.equals(openTagStart)) {
                TagLocation tagLocation = new TagLocation();
                tagLocation.startTagBegin = openTagMatcher.start();
                tagLocation.startTagEnd = openTagMatcher.end();
                Tag tag = null;
                try {
                    tag = (Tag) Class.forName("core.tags." + openTagMatcher.group(0).toUpperCase()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    tag = new HEAD();
                }
                tagLocation.tag = tag;
            } else if (minStart.equals(closeTagStart)) {

            } else {

            }
        }
        return (TagLocation[]) tagLocationList.toArray();
    }

    private static Tag convertLocationsToTag(TagLocation[] tagLocations) {
        // TODO
        return new HTML();
    }

    public static Tag compile(String text) {
        String textWithoutComments = text;
        try {
            textWithoutComments = removeComments(text);
        } catch (CommentWithoutEndException e) {
            e.printStackTrace();
        }
        String textWithoutExtraSpaces = removeExtraSpaces(textWithoutComments);
        TagLocation[] tagsLocations = detectTagsLocations(textWithoutExtraSpaces);
        Tag root = convertLocationsToTag(tagsLocations);
        HTML html = new HTML();
        HEAD head = new HEAD();
        TITLE title = new TITLE();
        title.addChildren(text);
        try {
            head.addChildren(title);
            html.addChildren(head);
            html.addChildren(text);

        } catch (UnsupportedChildrenTag unsupportedChildrenTag) {
            unsupportedChildrenTag.printStackTrace();

        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
        return html;
    }

}
