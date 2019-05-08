package core.tags;

import core.render.Drawer;

import static core.tags.CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

public class TD extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = GLOBAL_HTML_ATTRIBUTES;
    protected final static String[] CHILDREN_TYPES = CommonChildren.FLOW_CONTENT;

    @Override
    public void draw(Drawer drawer) {
        drawer.drawTableColumn();
        for (Object item : children) {
            if (item instanceof Tag)
                ((Tag) item).draw(drawer);
            else
                drawer.drawText((String) item);
        }
        drawer.endDraTableColumn();

    }


}
