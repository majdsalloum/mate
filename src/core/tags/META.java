package core.tags;

import core.render.Drawer;

public class META extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"name", "content"});
    public boolean requiresClosing() {
        return false;
    }

    @Override
    public void actualDraw(Drawer drawer) {
        // META DOESN'T DRAW ANY THING
    }
}
