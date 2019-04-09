package core.tags;

import core.render.Drawer;

public class LI extends  Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"type","value"});

    @Override
    public void draw(Drawer drawer) {

    }
}
