package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class TITLE extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

    @Override
    public void draw(Drawer drawer) {

    }
}
