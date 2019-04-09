package core.tags;

import core.render.Drawer;
import jdk.nashorn.api.tree.PropertyTree;

public class BODY extends Tag {
    protected final static String[] CHILDREN_TYPES={"A","B","U","I","SUB","SUP","BR","ABBR","BIG","SMALL","FONT","CENTER","HR","TABLE" ,"UL","OL","DL","DIV","FORM","INPUT","LABEL","TEXTAREA","SELECT","IMG"};
    protected final  static String[] SUPPORTED_ATTRIBUTES=CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String []{"bgcolor","background","text","link","vlink","alink"});
    @Override
    public  void draw(Drawer drawer)
    {
        if(!children.isEmpty())
            drawer.setTitle((String)children.get(0));
    }

}
