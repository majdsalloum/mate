package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

import java.util.Arrays;

public class INPUT extends Tag {
    protected final static String[] CHILDREN_TYPES = {};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,
            new String[]{"type", "value", "placeholder", "accept", "multiple"});

    @Override
    public boolean requiresClosing() {
        return false;
    }

    protected final String[] ALLOWED_TYPES = {
            "hidden", "text", "search", "tel", "url", "email", "password", "date", "month", "week", "time", "datetime-local",
            "number", "range", "color", "checkbox", "radio", "file", "submit", "image", "reset", "button"
    };


    protected String type = "text";
    protected String name = null;
    protected String value = null;
    protected String placeholder = null;
    protected String accept = null;
    protected Boolean multiple = false;
    protected Boolean checked=false;
    @Override
    public void actualDraw(Drawer drawer) {
        if (type.equals("file"))
            drawer.drawFileInput(name, accept, multiple);
        else if (type.equals("radio"))
            drawer.drawRadioInput(name,value,checked);
        else if (type.equals("checkbox"))
            drawer.drawCheckBoxInput(name ,value ,checked);
        else if (type.equals("reset")) {
            drawer.beginDrawButton("reset");
            drawer.endDrawButton();
        }
        else
            drawer.drawInput(type, name, value, placeholder);
    }

    @Override
    void validate() throws InvalidContentException {
        if (!Arrays.asList(ALLOWED_TYPES).contains(type.toLowerCase()))
            throw new InvalidContentException("input type " + type + "is not supported");

    }


}
