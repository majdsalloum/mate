package core.parser;

import core.exceptions.CommentWithoutEndException;
import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import core.exceptions.MoreThanOneRootException;
import core.tags.*;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HTMLParser {

    private static class TagLocation {
        Integer startTagBegin;
        Integer startTagEnd;
        Integer endTagBegin;
        Integer endTagEnd;
        Tag tag;
        TagLocation father;
        public boolean includes(TagLocation tagLocation)
        {
            return this.startTagEnd<tagLocation.startTagBegin &&
                    this.endTagBegin>tagLocation.endTagEnd;
        }
        TagLocation(Integer a, Integer b ,Integer c ,Integer d , Tag tag)
        {
            this.startTagBegin=a;
            this.startTagEnd=b;
            this.endTagBegin=c;
            this.endTagEnd=d;
            this.tag=tag;
        }
        TagLocation(){}
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

    private static LinkedList<TagLocation> detectTagsLocations(String text) {//todo : make it return tag location list
        final Pattern openTag = Pattern.compile("");
        final Pattern closeTag = Pattern.compile("");
        final Pattern openAndCloseTag = Pattern.compile("");
        LinkedList<TagLocation> tagLocationList = new LinkedList<>();
        LinkedList<TagLocation> tempStack = new LinkedList<>();
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
                    tag = new HEAD();//todo: change it to div
                }
                tagLocation.tag = tag;
                tagLocationList.add(tagLocation);
                tempStack.push(tagLocation);
            } else if (minStart.equals(closeTagStart)) {
                if (closeTagMatcher.group(0)!=tempStack.getFirst().toString())
                    throw new SyntaxException(tempStack.getFirst().toString()+" not closed correctly");
                else {
                    tempStack.pop();
                    tagLocationList.getFirst().endTagBegin=closeTagMatcher.start();
                    tagLocationList.getFirst().endTagEnd=closeTagMatcher.end();
                }
            } else {
                Tag tag;
                try {
                    tag = (Tag) Class.forName("core.tags." + openTagMatcher.group(0).toUpperCase()).getDeclaredConstructor().newInstance();
                }catch (Exception e)
                {
                    tag  = new HEAD();//todo : change to div
                }
                TagLocation tagLocation = new TagLocation() ;
                tagLocation.startTagBegin= openTagMatcher.start();
                tagLocation.endTagBegin=openTagMatcher.start();
                tagLocation.startTagEnd=openTagMatcher.end();
                tagLocation.endTagEnd=openTagMatcher.end();
                tagLocation.tag=tag;
                tagLocationList.add(tagLocation);
            }
        }
        return tagLocationList;
    }



    private static void setAttributes(LinkedList<TagLocation> list, String text)
    {

        for(TagLocation item : list) {
            String content = text.substring(item.startTagBegin+1, item.startTagEnd);
            content.replaceAll("-","_");
            Pattern pattern = Pattern.compile("(\\w+)(=('|'')([#@%^&!$*]?\\w+)(\\2))?");
            Matcher matcher = pattern.matcher(content);
            Map<String , Object> attributes = new HashMap<>();
            while (matcher.find())
            {
                attributes.put(matcher.group(1),matcher.group(3));
            }
            try {
                item.tag.setAttributes(attributes);
            } catch (InvalidContentException e) {
                e.printStackTrace();
            }
        }
    }
    private static void getTree(LinkedList<TagLocation> list) throws InvalidContentException , MoreThanOneRootException {
        TagLocation location  =list.get(0);
        for (int i=1;i<list.size();i++)
        {
            while (!location.includes(list.get(i))) {
                location=location.father;
                if (location==null) throw new MoreThanOneRootException(list.get(i).tag.getClass().getSimpleName()+" cannot be root");
            }
            location.tag.addChildren(list.get(i).tag);
            list.get(i).father=location;
            location=list.get(i);
        }
    }

    public static Tag compile(String text)  {
//        String textWithoutComments = text;
//        try {
//            textWithoutComments = removeComments(text);
//        } catch (CommentWithoutEndException e) {
//            e.printStackTrace();
//        }
//        String textWithoutExtraSpaces = removeExtraSpaces(textWithoutComments);
//        LinkedList<TagLocation> tagsLocations = detectTagsLocations(textWithoutExtraSpaces); // TODO : RENAME TO GET TAGS AND LOCATIONS
//        setAttributes(tagsLocations , textWithoutExtraSpaces);
//        return getTree(tagsLocations);
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
    public static void main(String[] args)
    {
        LinkedList<TagLocation> tagLocations =new LinkedList<>();
        tagLocations.add(new TagLocation(0,1,14,15,new HTML()));
        tagLocations.add(new TagLocation(2,3,8,9,new BODY()));
        tagLocations.add(new TagLocation(4,5,6,7,new TABLE()));
        tagLocations.add(new TagLocation(10,11,12,13,new BODY()));
        for (TagLocation tagLocation:tagLocations)
            System.out.println(tagLocation.tag);
        try {
            getTree(tagLocations);
            Tag root = tagLocations.get(0).tag;
            printTree(root);

        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
    }
    private static void printTree(Tag tag)
    {
        System.out.println(tag.toString());
        for(Object tag1 : tag.getChildren())
            printTree((Tag)tag1);
    }

}
