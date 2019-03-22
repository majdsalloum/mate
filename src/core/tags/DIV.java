package core.tags;

import core.render.Drawer;

public class DIV extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = {};

    @Override
    public void draw(Drawer drawer) {
        if (!children.isEmpty())
            drawer.drawText((String) children.get(0));
    }
}
