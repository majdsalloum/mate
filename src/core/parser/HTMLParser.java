package core.parser;

import core.exceptions.CommentWithoutEndException;
import core.exceptions.InvalidContentException;
import core.exceptions.UnsupportedChildrenTag;
import core.tags.*;


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

    private static TagLocation[] detectTagsLocations(String text) {
        // TODO
        return new TagLocation[0];
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
        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
        return html;
    }

}
