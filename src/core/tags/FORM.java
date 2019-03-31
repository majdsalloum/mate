package core.tags;

import core.render.Drawer;

public class FORM extends Tag {
    protected final static String[] CHILDREN_TYPES={"INPUT,SELECT,LABEL,TEXTAREA,FIELDSET,KEYGEN,OUTPUT"};
    protected  final static String[] SUPPORTED_ATTRIBUTES=CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"action,method,enctype,name, accept-charset,novalidate,target,autocomplete"});
    @Override
    public void draw(Drawer drawer) {
        if (!children.isEmpty())
            drawer.setTitle((String) children.get(0));

    }

}
