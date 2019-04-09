package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class TABLE extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"border"});
    protected final static String[] CHILDREN_TYPES = {"thead","tbody","tr","tfoot","colgroup"};

    void validate() throws InvalidContentException {
        if ( children.getClass() == TR.class)
            return;
        throw new InvalidContentException("TABLE TAG MUST HAVE AT LEAST ONE CHILDREN (TR)");
    }
    @Override
    public void draw(Drawer drawer) {

    }
}
