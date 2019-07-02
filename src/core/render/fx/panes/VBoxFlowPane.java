package core.render.fx.panes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import tests.ParsingTest;
import javafx.scene.paint.*;

public class VBoxFlowPane extends DrawerPane {
    FlowPane currentFlowPane;
    public VBoxFlowPane(Pane parent) {
        super(new VBox(new FlowPane(parent)));
        currentFlowPane = (FlowPane) parent.getChildren().get(0);
    }
    public VBoxFlowPane()
    {
        super(new VBox(new FlowPane()));
        currentFlowPane = (FlowPane) parent.getChildren().get(0);
    }

    @Override
    public void setMarign(double x1, double x2 ,double x3 ,double x4){
        VBox.setMargin(parent , new Insets(x1,x2,x3,x4));
    }
    @Override
    public void add (Node node) {
        ParsingTest.log(node.toString()+"   " + currentFlowPane.toString());
        //((FlowPane)parent.getChildren().get(parent.getChildren().size()-1)).getChildren().add(node);
        currentFlowPane.getChildren().add(node);
    }

    @Override
    public void drawLine()
    {
        FlowPane flowPane = new FlowPane();
        flowPane.setMinHeight(10);
        parent.getChildren().add(flowPane);
        currentFlowPane=flowPane;
    }
//    @Override
//    public void setAlign(Alignment align)
//    {
//
//        switch (align)
//        {
//            case CENTER:
//                ((VBox)parent).setAlignment(Pos.CENTER);
//            case RIGHT:
//                ((VBox)parent).setAlignment(Pos.BASELINE_RIGHT);
//            case LEFT:
//                ((VBox)parent).setAlignment(Pos.BASELINE_LEFT);
//
//        }
//    }

}
