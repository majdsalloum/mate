package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;
import core.render.Effect;
import core.render.actions.HrefAction;

public class A extends Tag {
    protected final static String[] CHILDREN_TYPES = {"table"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"href", "target", "rel", "type"});

    protected String href = null;

    protected String target = "_blank";

    @Override
    void validate() throws InvalidContentException {
        try {
            HrefAction.Target.valueOf(target.toLowerCase());
        } catch (Exception e) {
            throw new InvalidContentException("target is set to " + target + " which is not supported !");
        }
    }

    @Override
    public void actualDraw(Drawer drawer) {
        drawer.useAction(new HrefAction(href, HrefAction.Target.valueOf(target.toLowerCase())));
        for (Object i : children)
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());
        drawer.unUseAction();
    }
}
