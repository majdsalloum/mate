package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class DL extends Tag {
    protected final static String[] CHILDREN_TYPES = {"DD,DT"};
    protected final static String[] SUPPORTED_ATTRIBUTES =CommonAttributes.GLOBAL_HTML_ATTRIBUTES;
    void validate() throws InvalidContentException {
        if ( children.getClass() == DT.class&&children.getClass()==DD.class)
            return;
        throw new InvalidContentException("DD TAG MUST HAVE AT LEAST TOW CHILDREN (DT,DD)");
    }

    @Override
    public void draw(Drawer drawer) {

    }
}
