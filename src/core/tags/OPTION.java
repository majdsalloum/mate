package core.tags;

import core.render.Drawer;

public class OPTION extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"selected,label,value"});

    @Override
    public void draw(Drawer drawer) {
    }
}
