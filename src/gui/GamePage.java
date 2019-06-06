package gui;

import core.exceptions.UnSupportedSaveType;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;



public class GamePage extends Page {
    int score;
    int lives;
    int speed;
    Node gameNode;
    public GamePage(Window window, String path, String data) {
        super(window, path, data);
        initializeGame();
    }
    private void initializeGame() {
        score = 0;
        lives = 5;
        speed = 1;
        showGame();
    }
    private void showGame() {
        FlowPane flowPane = new FlowPane();
        Label label  = new Label();
        label.setGraphic(new ImageView(Images.logo));
        Button button = new Button("start");
        button.setOnAction(e->{
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(label);
            translateTransition.setToY(500);
            translateTransition.setDuration(new Duration(1000));
            translateTransition.play();
        });
        Circle holder = new Circle(5);
        holder.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        holder.setOnMousePressed(circleOnMousePressedEventHandler);
//        holder.setOnDragDetected((e)->{
//            func(holder);
//        });
        flowPane.getChildren().addAll(label,holder , button);
        gameNode = flowPane;
    }
    @Override
    public String toBeSaved() throws UnSupportedSaveType {
        throw new UnSupportedSaveType("game page");
    }

    @Override
    public Node getContent(){
        return gameNode;
    }





    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                }
            };
    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };
}
