package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListItem extends DrawerPane {
    public ListItem(Pane parent) {
        super(parent);
    }
    public ListItem(Pane parent , String Symbol)
    {
        super(parent);
    }

    @Override
    public void add(Node node) {
        ((HBox)parent).getChildren().addAll(node);
    }
}
