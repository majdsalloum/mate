package core.tags;

import core.render.Drawer;

public class HTML extends Tag {

    protected String lang = null;

    protected final static String[] CHILDREN_TYPES = {"body","head"};
    protected final static String[] SUPPORTED_ATTRIBUTES = {"lang"};

    @Override
    public void draw(Drawer drawer) {
        drawer.drawText(lang != null ? lang : "No Language");
    }
}
