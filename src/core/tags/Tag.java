package core.tags;

import core.exceptions.InvalidContentException;
import core.exceptions.UnsupportedAttributeException;
import core.exceptions.UnsupportedChildrenTag;
import core.render.Drawer;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

abstract public class Tag {

    protected List<Object> children = new ArrayList<>();
    // TODO INHERTIED TYPES AND Effect
    protected final static String[] SUPPORTED_ATTRIBUTES = {};
    protected final static String[] CHILDREN_TYPES = {};

    protected Map<String, Object> attributes;

    public boolean requiresClosing() {
        return true;
    }

    void validate() throws InvalidContentException {

    }

    public List<Object> getChildren() {
        return children;
    }

    public void setAttributes(Map<String, Object> attributes) throws InvalidContentException {
        try {
            List<String> SupportedAttributes = Arrays.asList(((String[]) getClass().getDeclaredField("SUPPORTED_ATTRIBUTES").get(this)));
//            SupportedAttributes = SupportedAttributes.stream().map(String::toLowerCase).collect(Collectors.toList());
            for (String key : attributes.keySet()) {
                if (SupportedAttributes.contains(key.toLowerCase())) {
                    final Object value = attributes.get(key);
                    try {
                        Field field = getClass().getDeclaredField(key.toLowerCase());
                        Class parameterType = field.getType();
                        if (!value.getClass().equals(parameterType))
                            throw new InvalidParameterException();
                        field.set(this, value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
                this.attributes = attributes;
            }
        } catch (Exception e) {
            throw new InvalidContentException(e.getClass().getName() + " " + e.getMessage());
        }
    }

    protected void beforeDraw(Drawer drawer) {
        drawer.captureLink();
    }

    abstract protected void actualDraw(Drawer drawer);


    protected void afterDraw(Drawer drawer) {
        drawer.linkTagAndVisibleItems(this,drawer.unCaptureLinkAndGetNewAdded());
    }
    public void draw(Drawer drawer) {
        beforeDraw(drawer);
        actualDraw(drawer);
        afterDraw(drawer);
    }

    public void addChildren(Tag tag) throws InvalidContentException {
        try {
            List<String> SupportedChildren = Arrays.asList(((String[]) getClass().getDeclaredField("CHILDREN_TYPES").get(this))).stream().map(String::toLowerCase).collect(Collectors.toList());
            if (!SupportedChildren.contains(tag.getClass().getSimpleName().toLowerCase()))
                throw new UnsupportedChildrenTag(tag.getClass().getName(), getClass().getName());
            children.add(tag);
        } catch (Exception e) {
            throw new InvalidContentException(e.getClass().getName() + " " + e.getMessage());
        }
    }

    public void addChildren(String string) {
        children.add(string);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
