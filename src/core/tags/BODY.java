package core.tags;

import core.render.Drawer;

public class BODY extends Tag {
    protected final static String[] CHILDREN_TYPES = CommonChildren.FLOW_CONTENT;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"bgcolor", "background", "text", "link", "vlink", "alink"});

    @Override
    public void actualDraw(Drawer drawer) {
        for (Object item : children) {
            if (item instanceof Tag)
                ((Tag) item).draw(drawer);
            else
                drawer.drawText(item.toString());
        }

    }

}
