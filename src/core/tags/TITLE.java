package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class TITLE extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;


    @Override
    void validate() throws InvalidContentException {
        if (children.size() != 1 || !(children.get(0) instanceof String))
            throw new InvalidContentException("Title must have exactly one string children");
    }

    @Override
    public void draw(Drawer drawer) {
        drawer.setTitle(children.get(0).toString());
    }
}
