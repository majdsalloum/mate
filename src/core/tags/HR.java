package core.tags;

import core.render.Drawer;

public class HR extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"size","width","align","color"});

    @Override
    public void actualDraw(Drawer drawer) {
    }
}
