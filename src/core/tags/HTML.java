package core.tags;

import core.exceptions.UnsupportedAttributeException;
import core.exceptions.UnsupportedChildrenTag;
import core.render.Drawer;

import java.util.Map;

public class HTML extends Tag {

    @Override
    public void setAttributes(Map<String, String> attributes) throws UnsupportedAttributeException {

    }

    @Override
    public void draw(Drawer drawer) {

    }

    @Override
    public void addChildren(Tag tag) throws UnsupportedChildrenTag {

    }
}
