package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class DrawerPane {
    Pane parent;

    public DrawerPane(Pane parent) {
        this.parent = parent;
    }

    public Pane getParent() {
        return parent;
    }

    public void add(Node node) {
        parent.getChildren().add(node);
    }

}
