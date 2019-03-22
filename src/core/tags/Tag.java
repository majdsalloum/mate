package core.tags;

import core.exceptions.UnsupportedAttributeException;
import core.exceptions.UnsupportedChildrenTag;
import core.render.Drawer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class Tag {

    protected List<Tag> children = new ArrayList<>();
    // TODO INHERTIED TYPES AND ATTRIBUTES
    protected final static String[] SUPPORTED_ATTRIBUTES = {};

    protected final static String[] CHILDREN_TYPES = {};


    public void setAttributes(Map<String, Object> attributes) throws UnsupportedAttributeException, NoSuchFieldException, IllegalAccessException {
        for (String key : attributes.keySet()) {
            if (!Arrays.asList(((String[]) getClass().getDeclaredField("SUPPORTED_ATTRIBUTES").get(this))).contains(key))
                throw new UnsupportedAttributeException(key, getClass().getName());
            final Object value = attributes.get(key);
            Field field = getClass().getDeclaredField(key.toLowerCase());
            Class parameterType = field.getType();
            if (!value.getClass().equals(parameterType))
                throw new InvalidParameterException();
            field.set(this, value);
        }
    }

    abstract public void draw(Drawer drawer);

    public void addChildren(Tag tag) throws UnsupportedChildrenTag, NoSuchFieldException, IllegalAccessException {
        if (!Arrays.asList(((String[]) getClass().getDeclaredField("CHILDREN_TYPES").get(this))).contains(tag.getClass().getSimpleName().toLowerCase()))
            throw new UnsupportedChildrenTag(tag.getClass().getName(), getClass().getName());
        children.add(tag);
    }
}
