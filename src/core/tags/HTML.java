package core.tags;

import core.render.Drawer;

public class HTML extends Tag {

    protected String lang = null;

    protected final static String[] CHILDREN_TYPES = {"body", "head"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"lang"});

    @Override
    public void draw(Drawer drawer) {
        for (Object item : children) {
            if (item instanceof Tag)
                ((Tag) item).draw(drawer);
            else
                drawer.drawText(item.toString());
        }
    }
}
