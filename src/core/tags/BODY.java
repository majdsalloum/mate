package core.tags;

import core.render.Drawer;

public class BODY extends Tag {
    protected final static String[] CHILDREN_TYPES={"table","a","b","U","I","SUB","SUP","BR","ABBR","BIG","SMALL","FONT","CENTER","HR" ,"UL","OL","DL","DIV","FORM","INPUT","LABEL","TEXTAREA","SELECT","IMG"};
    protected final  static String[] SUPPORTED_ATTRIBUTES=CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String []{"bgcolor","background","text","link","vlink","alink"});
    @Override
    public  void draw(Drawer drawer)
    {
        ((Tag)children.get(0)).draw(drawer);
//        if(!children.isEmpty())
//            drawer.setTitle((String)children.get(0));
    }

}
