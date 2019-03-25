package core.parser;

import core.tags.Tag;
import javafx.scene.control.SelectionMode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLItemAnalyzer {
    HTMLItem htmlItem;
    Tag tag;
    public HTMLItemAnalyzer(HTMLItem htmlItem)
    {
        this.htmlItem = htmlItem;
    }
    public void analyze()
    {
        String regex = "(\\w|\\R)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlItem.content);
        String tagName="";
        List<Pair<String,String> > attributes = new ArrayList<>();
        if (matcher.find())tagName=matcher.group();
        while (matcher.find())
        {
            String first = matcher.group();
            String seconde="";
            if (matcher.find())
            seconde =matcher.group();
            attributes.add(new Pair<>(first,seconde));
        }
        System.out.println(tagName);
        for (Pair<String,String> attr :attributes)
            System.out.println(attr.getKey()+" "+attr.getValue());
    }

}
