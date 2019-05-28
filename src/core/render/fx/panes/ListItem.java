package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ListItem extends DrawerPane {
    public ListItem(Pane parent) {
        super(new HBox(parent));
    }
    public ListItem(Node space1 ,Node space2 , Node symbol )
    {
        super(new HBox(space1,symbol, space2, new VBox(new FlowPane())));

    }
    @Override
    public void add(Node node) {
        node.setTranslateY(2);
        VBox vbox = (VBox)parent.getChildren().get(3);
        ((FlowPane)vbox.getChildren().get(vbox.getChildren().size()-1)).getChildren().add(node);
    }

    @Override
    public void drawLine() {
        VBox vbox = (VBox)parent.getChildren().get(3);
        FlowPane flowPane = new FlowPane();
        flowPane.setMinHeight(10);
        vbox.getChildren().add(flowPane);
    }
}
