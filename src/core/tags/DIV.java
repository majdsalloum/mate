package core.tags;

import core.render.Drawer;

public class DIV extends Tag {
    protected final static String[] CHILDREN_TYPES = CommonChildren.FLOW_CONTENT;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"style"});

    @Override
    public void draw(Drawer drawer) {

    }
}
