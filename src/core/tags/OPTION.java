package core.tags;

import core.render.Drawer;

public class OPTION extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"selected,label,value"});
    String label="";
    String value="";
    Boolean disabled=false;
    Boolean selected=false;
    @Override
    public void draw(Drawer drawer) {
        if (label.length()==0)
            label= (String) children.get(0);
        drawer.addOption(label,value,disabled,selected);
    }
    /**disabled from optgroup*/
    public void draw(Drawer drawer  ,Boolean disabled)
    {
        if (label.length()==0)
            label= (String) children.get(0);
        drawer.addOption(label,value,disabled,selected);
    }
}
