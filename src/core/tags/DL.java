package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class DL extends Tag {
    protected final static String[] CHILDREN_TYPES = {"DD,DT"};
    protected final static String[] SUPPORTED_ATTRIBUTES =CommonAttributes.GLOBAL_HTML_ATTRIBUTES;
    void validate() throws InvalidContentException {
        for(int i=0;i<children.size();i++)
        if ( children.get(i).getClass() == DT.class)
            return;
        throw new InvalidContentException("DD TAG MUST HAVE AT LEAST ONE CHILDREN (DT)");
    }

    @Override
    public void draw(Drawer drawer) {

    }
}
