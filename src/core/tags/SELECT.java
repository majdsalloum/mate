package core.tags;

import core.render.Drawer;

public class SELECT extends Tag {
    protected final static String[] CHILDREN_TYPES = {"OPTGROUP,OPTION"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"size,multiple"});

    @Override
    public void draw(Drawer drawer) {
        if (!children.isEmpty())
            drawer.setTitle((String) children.get(0));
    }

}
