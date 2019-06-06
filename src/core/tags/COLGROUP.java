package core.tags;

import core.render.Drawer;

import static core.tags.CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

public class COLGROUP extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(GLOBAL_HTML_ATTRIBUTES,new String[]{"span"});
    protected final static String[] CHILDREN_TYPES = {"col"};
    @Override
    public void actualDraw(Drawer drawer) {

    }
}
