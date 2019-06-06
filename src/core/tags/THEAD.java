package core.tags;

import core.render.Drawer;

import static core.tags.CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

public class THEAD extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = GLOBAL_HTML_ATTRIBUTES;
    protected final static String[] CHILDREN_TYPES = {"tr"};

    @Override
    public void actualDraw(Drawer drawer) {
        drawer.drawTableHeader();

        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());

        drawer.endDrawTableHeader();
    }
}
