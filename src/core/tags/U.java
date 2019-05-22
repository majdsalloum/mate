package core.tags;

import core.render.Drawer;
import core.render.Effect;

public class U extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

    @Override
    public void draw(Drawer drawer) {
        drawer.useEffect(Effect.FONT_UNDERLINE);
        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());

        drawer.unUseEffect(Effect.FONT_UNDERLINE);
    }
}
