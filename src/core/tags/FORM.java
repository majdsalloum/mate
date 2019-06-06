package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;
import core.render.actions.FormAction;

public class FORM extends Tag {
    protected final static String[] CHILDREN_TYPES = CommonChildren.FLOW_CONTENT;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"action", "method", "enctype", "name", "accept-charset", "novalidate", "target", "autocomplete"});


    protected String method = "GET";
    protected String action = "";

    @Override
    void validate() throws InvalidContentException {
        // TODO VALIDATE THAT NO NESTED FORMS
    }
    @Override
    public void actualDraw(Drawer drawer) {
        drawer.useAction(new FormAction(method, action, drawer));

        for (Object i : children) {
            if (i instanceof Tag)
                ((Tag) i).draw(drawer);
            else
                drawer.drawText(i.toString());
        }

        drawer.unUseAction();
    }


}
