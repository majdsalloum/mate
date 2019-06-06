package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

import static core.tags.CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

public class TR extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = GLOBAL_HTML_ATTRIBUTES;
    protected final static String[] CHILDREN_TYPES = {"td","th"};

    @Override
    public void actualDraw(Drawer drawer) {
        drawer.drawTableRow();

        for (Object item : children)
        {
            if (item instanceof Tag)
                ((Tag) item).draw(drawer);
            else
                drawer.drawText((String) item);
        }

        drawer.endDrawTableRow();
    }
}
