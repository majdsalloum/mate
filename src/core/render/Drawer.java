package core.render;


import core.render.actions.Action;
import core.render.actions.FormAction;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Drawer {
    final Integer LIST_TRANSLATE_X = 32;

    protected int[] effectsUsages = new int[Effect.values().length];
    protected LinkedList<Action> actions = new LinkedList<>();
    protected LinkedList<Alignment> alignments = new LinkedList<>();

    protected String baseUrl;

    public String getRelativePath(String relativePath) {
        if (URI.create(relativePath).getScheme() == null) {
            try {
                return (new URL(new URL(baseUrl), relativePath)).toString();
            } catch (MalformedURLException exception) {
                return "";
            }
        } else
            return relativePath;
    }

    public Drawer(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public Boolean hasEffect(Effect effect) {
        return effectsUsages[effect.ordinal()] > 0;
    }

    public void useAction(Action action) {
        actions.add(action);
    }

    public void unUseAction() {
        actions.removeLast();
    }

    public boolean hasAction(Class<? extends Action> action) {
        for (Action action1 : actions)
            if (action.isInstance(action1)) return true;
        return false;
    }

    public <T extends Action> List<T> getAllActions(Class<T> action) {
        return (List<T>) actions.stream().filter(x -> x.getClass() == action).collect(Collectors.toList());
    }

    public <T extends Action> T getFirstAction(Class<T> action) {
        List<T> allActions = getAllActions(action);
        if (allActions.size() > 0)
            return allActions.get(0);
        else
            return null;
    }

    public <T extends Action> T getLastAction(Class<T> action) {
        List<T> allActions = getAllActions(action);
        if (allActions.size() > 0)
            return allActions.get(allActions.size() - 1);
        else
            return null;
    }

    public void useEffect(Effect effect) {
        effectsUsages[effect.ordinal()]++;
    }

    public void unUseEffect(Effect effect) {
        if (effectsUsages[effect.ordinal()] == 0) {
            System.out.println("Warning: effect " + effect + " is not used and you're trying to unuse it");
            return;
        }
        effectsUsages[effect.ordinal()]--;
    }

    public void useAlignment(Alignment alignment) {
        alignments.add(alignment);
    }

    public void unUseAlignment() {
        if (alignments.isEmpty()) {
            System.out.println("Warning: You're trying to unuse alignment while it's not being used!");
            return;
        }
        alignments.pop();
    }

    abstract protected void resetForm(FormAction formAction);

    abstract protected void submitForm(FormAction formAction);

    abstract public void drawFileInput(String name,String accept, Boolean multiple);

    abstract public void drawInput(String type, String name, String value, String placeHolder);

    abstract public void beginDrawButton(String type);

    abstract public void endDrawButton();

    abstract public void drawText(String text);

    abstract public void setTitle(String text);

    abstract public void drawImage(String path);

    abstract public void drawTable();
    abstract public void endDrawTable();

    abstract public void drawCaption();
    abstract public void endDrawCaption();

    abstract public void drawTableHeader();
    abstract public void endDrawTableHeader();

    abstract public void drawTableColumn();
    abstract public void endDraTableColumn();

    abstract public void drawTableRow();
    abstract public void endDrawTableRow();

    abstract public void drawUnOrderedList(String symbol);
    abstract public void endDrawUnOrderedList();

    abstract public void drawListItem();
    abstract public void endDrawListItem();

    abstract public void drawOrderedList(String start , String symbol);
    abstract public void endDrawOrderedList();

    abstract public void drawNewLine();


}
