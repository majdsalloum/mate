package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class TITLE extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.GLOBAL_HTML_ATTRIBUTES;
    void validate() throws InvalidContentException {
        for (Object item : children) {
            if (!(item instanceof String))
                throw new InvalidContentException("title must have only text ");
        }
    }
    @Override
    public void draw(Drawer drawer) {
        if (!children.isEmpty())
        drawer.setTitle((String) children.get(0));
    }
}
