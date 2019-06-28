package core.tags;

import core.render.Alignment;
import core.render.Drawer;

public class P extends Tag {
    protected final static String[] CHILDREN_TYPES = BODY.CHILDREN_TYPES;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"align"});

    String align;
    @Override
    public void actualDraw(Drawer drawer) {
        if (align!=null)
        {
            switch (align.toLowerCase())
            {
                case "left":
                    drawer.drawParagraph();
                case "right":
                    drawer.drawParagraph(Alignment.RIGHT);
                case "center":
                    drawer.drawParagraph(Alignment.CENTER);
            }
        }
        drawer.drawParagraph();
        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());
        drawer.endDrawParagraph();

    }

}