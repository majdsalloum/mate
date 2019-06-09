package gui;

import core.exceptions.UnSupportedSaveType;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Builder;
import javafx.util.Duration;

import java.util.Random;


public class GamePage extends Page {

    Node gameNode;
    public GamePage(Window window, String path, String data) {
        super(window, path, data);
        initializeGame();
        refreshGame();
    }
    Player player;
    Label holder;
    Label drop;
    double dropX =0, dropY =0, holderX =0, holderY=500;
    double animationTime=2000;
    Label scoreLabel;
    Label livesLabel;
    ToolBar toolBar;
    FlowPane gameGrid;
    private void initializeGame() {
        player = new Player();
        scoreLabel = new Label("score 0");
        livesLabel = new Label("lives 0");
        toolBar = new ToolBar(scoreLabel , livesLabel);
        gameGrid = new FlowPane();
        drop = new Label();
        holder = new Label();
        drop.setGraphic(new ImageView(Images.getImage("..\\img\\herb.png")));
        holder.setGraphic(new ImageView(Images.getImage("..\\img\\holder.png")));
        holder.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        holder.setOnMousePressed(circleOnMousePressedEventHandler);
        drop.setTranslateX(dropX);
        drop.setTranslateY(dropY);
        holder.setTranslateX(holderX);
        holder.setTranslateY(holderY);
        ((FlowPane)gameGrid).getChildren().addAll(drop , holder);
        gameNode = new VBox(toolBar , gameGrid);

    }
    private void refreshGame()
    {
        scoreLabel.setText("score "+player.getScore());
        livesLabel.setText("lives "+player.getLives());
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(drop);
        translateTransition.setToY(500);
        translateTransition.setDuration(new Duration(animationTime/player.getSpeedFactor()));
        translateTransition.play();
        translateTransition.setOnFinished((t)->{
            double holderMinX = holder.getTranslateX() + holder.getLayoutX();
            double holderMaxX = holderMinX + holder.getWidth();
            double dropMinX = drop.getTranslateX() + drop.getLayoutX();
            double dropMaxX = dropMinX + drop.getWidth();
            //System.out.println(holderX + " " + minX + " " + maxX);
            if ((dropMinX>=holderMinX && dropMinX<=holderMaxX)||
                    (dropMaxX>=holderMinX && dropMaxX<=holderMaxX))
            {
                player.setScore(player.getScore()+1);
                System.out.println("Ok");
            }
            else {player.setLives(player.getLives()-1);
                System.out.println("No");}
            if (player.getLives()>0)
            reLocate();
            else showLose();
        });
    }

    private void showLose(){
        scoreLabel.setText("score "+player.getScore());
        livesLabel.setText("lives "+player.getLives());
        Button button = new Button("Play Again");
        Label label = new Label("you lose , your score is " + player.getScore());
        button.setFont(new Font(50));
        label.setFont(new Font(50));
        button.setOnAction((e)->{
            window.setPageAndUpdate(new GamePage(window , "game" ,null));
        });
        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.getChildren().setAll(label , button);
        gameGrid.setOrientation(Orientation.VERTICAL);
        button.setAlignment(Pos.CENTER);
    }

    private void reLocate(){
      drop.setTranslateY(dropY);
      Double rand= (new Random().nextDouble()*2000)%1300 ;
      drop.setTranslateX(rand);
      refreshGame();
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
                    orgTranslateX = ((Node)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Node)(t.getSource())).getTranslateY();
                }
            };
    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    //double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    //double newTranslateY = orgTranslateY + offsetY;
                    ((Node)(t.getSource())).setTranslateX(newTranslateX);
                    //((Circle)(t.getSource())).setTranslateY(newTranslateY);
                }
            };





    class Player{
        int lives;
        int score;
        double speedFactor;
         Player()
        {
            lives = 5;
            score = 0;
            speedFactor = 1;
        }
         double getSpeedFactor() {
            int level = score /5;
            return speedFactor + (level*0.1);
        }

         int getLives() {
            return lives;
        }

         void setLives(int lives) {
            this.lives = lives;
        }

         int getScore() {
            return score;
        }

         void setScore(int score) {
            this.score = score;
        }
    }

}
