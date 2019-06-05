package core.render.fx.panes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

    public void drawLine()
    {
        parent = new VBox(parent);
    }

    public void setMarign(double x1, double x2 ,double x3 ,double x4){}
}
