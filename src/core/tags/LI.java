package core.tags;

import core.render.Drawer;

public class LI extends  Tag {
    protected final static String[] CHILDREN_TYPES = BODY.CHILDREN_TYPES;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"type","value"});

    @Override
    public void actualDraw(Drawer drawer) {
        drawer.drawListItem();
        for (Object item  : children)
        {
            if (item instanceof Tag)
                ((Tag)item).draw(drawer);
            else drawer.drawText((String) item);
        }
        drawer.endDrawListItem();
    }
}
