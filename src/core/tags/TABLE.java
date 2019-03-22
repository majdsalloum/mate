package core.tags;

import core.render.Drawer;

public class TABLE extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"border"});
    protected final static String[] CHILDREN_TYPES = {"thead","tbody","tr","tfoot","colgroup"};
    @Override
    public void draw(Drawer drawer) {

    }
}
