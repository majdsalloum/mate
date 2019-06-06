package core.tags;

import core.render.Drawer;
import core.render.Effect;

public class B extends Tag {
    protected final static String[] CHILDREN_TYPES = BODY.CHILDREN_TYPES;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

    @Override
    public void actualDraw(Drawer drawer) {
        drawer.useEffect(Effect.FONT_BOLD);
        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());
        drawer.unUseEffect(Effect.FONT_BOLD);

    }

}
