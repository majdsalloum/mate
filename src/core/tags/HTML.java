package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class HTML extends Tag {

    protected String lang = null;

    protected final static String[] CHILDREN_TYPES = {"body", "head"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"lang"});//todo manifest

    @Override
    void validate() throws InvalidContentException {
       if (children.size() == 2 && children.get(0).getClass() == HEAD.class && children.get(1).getClass() == BODY.class)
           return;
       throw new InvalidContentException("HTML TAG MUST HAVE EXACTLY TWO CHILDREN ( HEAD THEN BODY )");
    }

    @Override
    public void draw(Drawer drawer) {
        for (Object item : children) {
            if (item instanceof Tag)
                ((Tag) item).draw(drawer);
            else
                drawer.drawText(item.toString());
        }
    }
}
