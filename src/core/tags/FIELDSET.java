package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class FIELDSET extends Tag {
    protected final static String[] CHILDREN_TYPES = {"legend"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;


    @Override
    public void actualDraw(Drawer drawer) {

    }
}
