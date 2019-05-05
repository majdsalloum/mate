package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ListDrawPane extends DrawerPane {
    public ListDrawPane(Pane parent) { super(new VBox()); }
    public String symbol = "disk";
    @Override
    public void add(Node node)
    {
        ((VBox)parent).getChildren().add(node);
    }

    public ListDrawPane(Pane parent, String symbol) {
        super(parent);
        if (symbol!=null)
        this.symbol = symbol;
    }
}
