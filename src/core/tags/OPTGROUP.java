package core.tags;

import core.render.Drawer;


public class OPTGROUP extends Tag {
    protected final static String[] CHILDREN_TYPES = {"OPTION"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"label"});
    String label="";
    Boolean disabled=false;
    @Override
    public void draw(Drawer drawer) {
        drawer.drawOptionGroup(label);
        for(Object item : children)
            if (item instanceof OPTION)
            {
                if (disabled)
                    ((OPTION)item).draw(drawer , false);
                else ((Tag)item).draw(drawer);
            }
        drawer.endDrawOptionGroup();
    }
}
