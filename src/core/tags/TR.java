package core.tags;

import core.render.Drawer;

import static core.tags.CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

public class TR extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = GLOBAL_HTML_ATTRIBUTES;
    protected final static String[] CHILDREN_TYPES = {"td","th"};
    @Override
    public void draw(Drawer drawer) {

    }
}
