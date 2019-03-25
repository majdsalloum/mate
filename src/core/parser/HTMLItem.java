package core.parser;

import java.util.ArrayList;
import java.util.List;


enum HTML_ITEM_TYPE{
    START_TAG,END_TAG,SINGLE_TAG,TEXT_CONTENT,NON
}
public class HTMLItem {
    String content;
    HTMLItem father =this;
    List<HTMLItem> children = new ArrayList<>();
    HTML_ITEM_TYPE type = HTML_ITEM_TYPE.NON;
    public HTMLItem(){}
    public HTMLItem(String content , HTML_ITEM_TYPE type)
    {
        this.content =content;
        this.type = type;
    }
    public HTMLItem addChild(String content , HTML_ITEM_TYPE type)
    {
        HTMLItem child = new HTMLItem(content , type);
        child.father = this;
        children.add(child);
        return child;
    }
    public void surfTree()
    {
        System.out.println(content + " child of :" + (father!=null?father.content:"no father"));
        for (HTMLItem htmlItem : children)
            htmlItem.surfTree();
    }
    public HTMLItem getFather()
    {
        return father;
    }
}