package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class SELECT extends Tag {
    protected final static String[] CHILDREN_TYPES = {"OPTGROUP","OPTION"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"size,multiple"});

    void validate() throws InvalidContentException {
        for(int i=0;i<children.size();i++)
        if ( children.get(i).getClass() == OPTION.class)
            return;
        throw new InvalidContentException("SELECT TAG MUST HAVE AT LEAST ONE CHILDREN (OPTION)");
    }

    @Override
    public void draw(Drawer drawer) {

    }

}
