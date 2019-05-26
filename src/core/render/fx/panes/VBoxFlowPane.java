package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VBoxFlowPane extends DrawerPane {
    public VBoxFlowPane(Pane parent) {
        super(new VBox(new FlowPane()));
    }
    @Override
    public void add (Node node) {
        ((FlowPane)parent.getChildren().get(parent.getChildren().size()-1)).getChildren().add(node);
    }

    @Override
    public void addInnewLine(Node node) {
        ((VBox)parent).getChildren().add(new FlowPane());
        add(node);
    }
}
