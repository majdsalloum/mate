package core.tags;

import core.render.Drawer;

public class TITLE extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

    @Override
    public void draw(Drawer drawer) {
        if (!children.isEmpty())
            drawer.setTitle((String) children.get(0));
    }
}
