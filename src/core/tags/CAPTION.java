package core.tags;

import core.render.Drawer;

public class CAPTION extends Tag {

    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;


    @Override
    public void actualDraw(Drawer drawer) {
        drawer.drawCaption();
        for (Object item2 : children) {
            if (!(item2 instanceof Tag)) {
                drawer.drawText((String) item2);
            } else ((Tag) item2).draw(drawer);
        }
        drawer.endDrawCaption();
    }
}
