package core.tags;

import core.exceptions.UnsupportedChildrenTag;
import core.render.Drawer;

import java.util.Collections;

public class HTML extends Tag {

    protected String lang = null;

    protected final static String[] CHILDREN_TYPES = {"body", "head"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"lang"});

    @Override
    public void draw(Drawer drawer) {
        for (Object element : children) {
            if (element instanceof Tag) ((Tag) element).draw(drawer);
            else if (element instanceof String) {
                drawer.drawText((String) element);
            }
        }
    }
}
