package core.render.fx.panes;

import core.render.Symbols.OrderedSymbol;
import core.render.Symbols.UnOrderedSymbol;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OrderedListDrawPane extends DrawerPane {
    public OrderedListDrawPane(Pane parent) { super(new VBox()); }
    OrderedSymbol symbol ;
    @Override
    public void add(Node node)
    {
        ((VBox)parent).getChildren().add(node);
    }

    public OrderedSymbol getSymbol() {
        return symbol;
    }

    public OrderedListDrawPane(Pane parent, int start , String symbol) {
        super(parent);
        if (symbol!=null)
            this.symbol = new OrderedSymbol(start ,symbol);
        else
            this.symbol=new OrderedSymbol("disk");
    }
}
