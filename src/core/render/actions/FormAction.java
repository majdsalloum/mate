package core.render.actions;

import core.render.Drawer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FormAction implements Action {

    private String method;
    private String action;
    private Drawer drawer;
    private HashMap<String, Object> fields = new HashMap<>();

    public FormAction(String method, String action, Drawer drawer) {
        this.method = method;
        this.action = action;
        this.drawer = drawer;
    }

    public void setAttribute(String key, Object value) {
        fields.put(key, value);
    }

    public Object getAttribute(String key) {
        return fields.get(key);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public String getMethod() {
        return method;
    }

    public String getAction() {
        return action;
    }
}
