package core.tags;

import core.render.Drawer;

public class BR extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;


    public boolean requiresClosing() {
        return false;
    }


    @Override
    public void actualDraw(Drawer drawer) {
        drawer.drawNewLine();
    }
}
