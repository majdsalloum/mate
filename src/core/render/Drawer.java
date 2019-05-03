package core.render;


import java.util.LinkedList;

public abstract class Drawer {
    protected int[] effectsUsages = new int[Effect.values().length];

    protected LinkedList<Alignment> alignments = new LinkedList<>();

    public Boolean hasEffect(Effect effect) {
        return effectsUsages[effect.ordinal()] > 0;
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

    abstract public void drawTable();

    abstract public void endDrawTable();

    abstract public void drawCaption();

    abstract public void endDrawCaption();

    abstract public void drawHeader();

    abstract public void endDrawHeader();

    abstract public void drawTableColumn();

    abstract public void endDraTableColumn();

    abstract public void drawTableRow();

    abstract public void endDrawTableRow();


}
