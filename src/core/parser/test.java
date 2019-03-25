package core.parser;

import core.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.*;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args)
    {
        String HTML = "<html lolo='0'>" +
                "<Body>" +
                "Shadi ss"+
                "</body>" +
                "<lolo>" +
                "</lolo>" +
                "</html>";
        getTag(HTML);
    }
    private static void getTag(String text)
    {
        Stack<String> tags  = new Stack<>();
       String regex = "(<\\w(.|\\s)*?>)|(</\\w+>)|(\\w+)|(\\s)";
       HTMLItem root = new HTMLItem("Root" , HTML_ITEM_TYPE.NON);
       HTMLItem iterator = root;
       Pattern pattern =Pattern.compile(regex);
       Matcher matcher =pattern.matcher(text);
       while (matcher.find())
       {
           String tag = matcher.group();
           if (tag.equals(matcher.group(1)))
           {
               //todo: tags.push(tag); get tag name and PUSH it
               iterator =iterator.addChild(tag , HTML_ITEM_TYPE.START_TAG);
               System.out.println(tag + " pushed and added");
           }
           else if (tag.equals(matcher.group(2)))
           {
               if(iterator.father!=null)
                   iterator = iterator.getFather();
               //tags.pop();
               System.out.println(tag + " closed");

           }
           else if(tag.equals(matcher.group(3))||tag.equals(matcher.group(4)))
           {
               iterator.addChild(tag , HTML_ITEM_TYPE.TEXT_CONTENT);
               System.out.println(tag + " added as content");

           }
       }
       root.surfTree();

    }

}
