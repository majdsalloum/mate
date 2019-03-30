package core.parser;

import core.exceptions.UnsupportedChildrenTag;
import core.tags.*;


public class HTMLParser {

    private static class TagLocation {
        Integer startTagBegin;
        Integer startTagEnd;
        Integer endTagBegin;
        Integer endTagEnd;
    }

    private static String removeExtraSpaces(String text) {
        return text.replaceAll("\\s+", " ");
    }

    private static String removeComments(String text) {
        return text.replaceAll("<!--(?:.|\\s)+?-->", "");
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
        String textWithoutExtraSpaces = removeExtraSpaces(text);
        String textWithoutComments = removeComments(text);
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
        } catch (UnsupportedChildrenTag | NoSuchFieldException | IllegalAccessException unsupportedChildrenTag) {
            unsupportedChildrenTag.printStackTrace();
        }
        return html;
    }

}
