package core.tags;

import core.render.Drawer;

public class P extends Tag {
    protected final static String[] CHILDREN_TYPES = BODY.CHILDREN_TYPES;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"align"});

    @Override
    public void draw(Drawer drawer) {
        drawer.drawParagraph();
        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());
        drawer.endDrawParagraph();

    }

}