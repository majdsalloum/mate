package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

import static core.tags.CommonAttributes.GLOBAL_HTML_ATTRIBUTES;

public class TR extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = GLOBAL_HTML_ATTRIBUTES;
    protected final static String[] CHILDREN_TYPES = {"td","th"};
    void validate() throws InvalidContentException {
        //if ( children.getClass() == TD.class)
            return;
       // throw new InvalidContentException("TR TAG MUST HAVE AT LEAST ONE CHILDREN (TD )");
    }
    @Override
    public void draw(Drawer drawer) {

    }
}
