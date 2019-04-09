package core.tags;

import core.render.Drawer;

public class FONT extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"face","size","color"});

    @Override
    public void draw(Drawer drawer) {

    }
}
