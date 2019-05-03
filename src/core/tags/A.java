package core.tags;

import core.render.Drawer;
import core.render.actions.Action;
import core.render.actions.HrefAction;

public class A extends Tag {
    protected final static String[] CHILDREN_TYPES = {"table"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"href", "target", "rel", "type"});

    protected String href = null;

    @Override
    public void draw(Drawer drawer) {
        drawer.useAction(new HrefAction(href, HrefAction.Target._blank));
        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());
        drawer.unUssAction();
    }
}
