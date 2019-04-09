package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class OL extends Tag {
    protected final static String[] CHILDREN_TYPES = {"LI"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"start, reversed,type"});

    void validate() throws InvalidContentException {
        if ( children.getClass() == LI.class)
            return;
        throw new InvalidContentException("OL TAG MUST HAVE AT LEAST ONE CHILDREN (LI )");
    }

    @Override
    public void draw(Drawer drawer) {

    }

}
