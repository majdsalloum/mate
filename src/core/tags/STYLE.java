package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class STYLE extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String []{" type","media","scoped"});


    @Override
    public void draw(Drawer drawer) {

    }
}
