package core.tags;

import core.render.Drawer;
import core.render.Effect;

public class I extends Tag{
        protected final static String[] CHILDREN_TYPES = {};
        protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

        @Override
        public void actualDraw(Drawer drawer) {
                drawer.useEffect(Effect.FONT_ITALIC);
                for (Object i : children)
                 if (i instanceof Tag)
                        ((Tag) i).draw(drawer);
                else
                        drawer.drawText(i.toString());
                drawer.unUseEffect(Effect.FONT_ITALIC);
        }

}
