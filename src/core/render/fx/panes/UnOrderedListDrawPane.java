package core.render.fx.panes;

import core.render.Symbols.UnOrderedSymbol;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UnOrderedListDrawPane extends DrawerPane {
    public UnOrderedListDrawPane(Pane parent) { super(new VBox()); }
    UnOrderedSymbol symbol ;
    @Override
    public void add(Node node)
    {
        ((VBox)parent).getChildren().add(node);
    }

    public UnOrderedSymbol getSymbol() {
        return symbol;
    }

    public UnOrderedListDrawPane(Pane parent, String symbol) {
        super(parent);
        if (symbol!=null)
            this.symbol = new UnOrderedSymbol(symbol);
        else
            this.symbol=new UnOrderedSymbol("disk");
    }
}
