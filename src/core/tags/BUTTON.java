package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

import java.util.Arrays;

public class BUTTON extends Tag {

    protected final static String[] CHILDREN_TYPES = CommonChildren.PHRASING_CONTENT;
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,
            new String[]{"type", "value", "name", "text"});


    protected String[] ALLOWED_TYPES = {
            "button", "reset", "submit"
    };
    protected String value = null;
    protected String name = null;
    protected String type = "button";
    protected String text = null;


    @Override
    void validate() throws InvalidContentException {
        if (!Arrays.asList(ALLOWED_TYPES).contains(type.toLowerCase()))
            throw new InvalidContentException("Unknown type " + type);
    }

    @Override
    public void draw(Drawer drawer) {
        drawer.beginDrawButton(type.toLowerCase());
        if (value != null)
            drawer.drawInput("hidden", name, value, null);
        if (children.size() == 0 && text != null)
            drawer.drawText(text);
        for (Object item2 : children) {
            if (!(item2 instanceof Tag)) {
                drawer.drawText((String) item2);
            } else
                ((Tag) item2).draw(drawer);
        }
        drawer.endDrawButton();
    }
}
