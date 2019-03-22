package core.tags;

import core.exceptions.UnsupportedAttributeException;
import core.exceptions.UnsupportedChildrenTag;
import core.render.Drawer;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class Tag {

    protected List<Object> children = new ArrayList<>();
    // TODO INHERTIED TYPES AND ATTRIBUTES
    protected final static String[] SUPPORTED_ATTRIBUTES = {};
    protected final static String[] CHILDREN_TYPES = {};

    protected Map<String, Object> attributes;

    public void setAttributes(Map<String, Object> attributes) throws UnsupportedAttributeException, NoSuchFieldException, IllegalAccessException {
        for (String key : attributes.keySet()) {
            if (!Arrays.asList(((String[]) getClass().getDeclaredField("SUPPORTED_ATTRIBUTES").get(this))).contains(key))
                throw new UnsupportedAttributeException(key, getClass().getName());
            final Object value = attributes.get(key);
            try {
                Field field = getClass().getDeclaredField(key.toLowerCase());
                Class parameterType = field.getType();
                if (!value.getClass().equals(parameterType))
                    throw new InvalidParameterException();
                field.set(this, value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        this.attributes = attributes;
    }

    abstract public void draw(Drawer drawer);

    public void addChildren(Tag tag) throws UnsupportedChildrenTag, NoSuchFieldException, IllegalAccessException {
        if (!Arrays.asList(((String[]) getClass().getDeclaredField("CHILDREN_TYPES").get(this))).contains(tag.getClass().getSimpleName().toLowerCase()))
            throw new UnsupportedChildrenTag(tag.getClass().getName(), getClass().getName());
        children.add(tag);
    }

    public void addChildren(String string) {
        children.add(string);
    }

}
