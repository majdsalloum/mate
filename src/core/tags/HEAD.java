package core.tags;

import core.render.Drawer;

public class HEAD extends Tag {


    protected final static String[] CHILDREN_TYPES = {"title","link","meta","style"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

    @Override
    public void draw(Drawer drawer) {

    }
}
