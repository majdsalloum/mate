package core.render;


import core.render.actions.Action;
import javafx.scene.image.Image;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;

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
}
