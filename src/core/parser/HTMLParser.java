package core.parser;

import core.exceptions.UnsupportedChildrenTag;
import core.tags.*;
import java.util.regex.*;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HTMLParser {
    public static Tag compile(String text) {
        LinkedList<String> stack = new LinkedList<>();
        text = text.replaceAll("(.+?)\\s{2,}", "$1 ");
        final Pattern TAG_MATCHER = Pattern.compile("<(\\w+)(.|\\s)*?>");
        Matcher matcher = TAG_MATCHER.matcher(text);
        Stream<MatchResult> stream =  matcher.results();
        while (stream.iterator().hasNext()) {
            MatchResult result = stream.iterator().next();
        }
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return html;
    }

}
