package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListItem extends DrawerPane {
    public ListItem(Pane parent) {
        super(parent);
    }

    @Override
    public void add(Node node) {
        ((HBox)parent).getChildren().add(node);
    }
}
