package core.tags;

import core.exceptions.InvalidContentException;
import core.exceptions.UnsupportedAttributeException;
import core.exceptions.UnsupportedChildrenTag;
import core.render.Drawer;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

abstract public class Tag {

    protected List<Object> children = new ArrayList<>();
    // TODO INHERTIED TYPES AND ATTRIBUTES
    protected final static String[] SUPPORTED_ATTRIBUTES = {};
    protected final static String[] CHILDREN_TYPES = {};

    protected Map<String, Object> attributes;

    public final static Boolean REQUIRE_CLOSING = true;

    void validate() throws InvalidContentException {

    }

    public List<Object> getChildren() {
        return children;
    }

    public void setAttributes(Map<String, Object> attributes) throws InvalidContentException {
        try {
            for (String key : attributes.keySet()) {
                if (!Arrays.asList(((String[]) getClass().getDeclaredField("SUPPORTED_ATTRIBUTES").get(this))).contains(key)) {
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

    abstract public void draw(Drawer drawer);

    public void addChildren(Tag tag) throws InvalidContentException {
        try {
            if (!Arrays.asList(((String[]) getClass().getDeclaredField("CHILDREN_TYPES").get(this))).contains(tag.getClass().getSimpleName().toLowerCase()))
                throw new UnsupportedChildrenTag(tag.getClass().getName(), getClass().getName());
            children.add(tag);
        } catch (Exception e) {
            throw new InvalidContentException(e.getClass().getName() + " " + e.getMessage());
        }
    }
    //todo : ====> change all children type to lower case in each tag <=====

    public void addChildren(String string) {
        children.add(string);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
