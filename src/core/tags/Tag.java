package core.tags;

import core.exceptions.*;
import core.render.Drawer;

import java.util.Map;

public abstract class Tag {

    protected Tag[] children;

    final static String[] childrenTypes = new String[0];

    abstract public void setAttributes(Map<String,String> attributes) throws UnsupportedAttributeException;

    abstract public void draw(Drawer drawer);

    abstract public void addChildren(Tag tag) throws UnsupportedChildrenTag;
}
