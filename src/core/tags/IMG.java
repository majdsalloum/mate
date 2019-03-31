package core.tags;

import core.render.Drawer;

public class IMG extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"src,alt,height,width,usemap,ismap,border"});

    @Override
    public void draw(Drawer drawer) {
        if (!children.isEmpty())
            drawer.setTitle((String) children.get(0));
    }
}
