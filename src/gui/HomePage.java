package gui;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.*;

public class HomePage extends Page{
    private Label matteLabel;
    private TextField textSearch;
    private Button search;
    private VBox homePage;
    public HomePage()
    {
        path = "matte://home";
        homePage=new VBox();
        homePage.setSpacing(10);
        homePage.setAlignment(Pos.CENTER);
        matteLabel = new Label("Matté");
        matteLabel.setFont(Font.font(200));
        matteLabel.setTextFill(Color.GREEN);

        /**Some animation */
        FadeTransition transition = new FadeTransition(Duration.seconds(3), matteLabel);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        //transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
        textSearch = new TextField();
        textSearch.setMaxWidth(500);
        search = new Button("Search");
        search.setFont(Font.font(20));
        search.setTextFill(Color.GREEN);
        homePage.getChildren().addAll(matteLabel,textSearch,search);
        search.setOnAction(event -> {
            if (textSearch.getText()!=null)
            window.search(textSearch.getText());
        });


        GridPane gridPane =new GridPane();
        Label label1=new Label("lab1");
        Label label2=new Label("lab2");
        Label label3=new Label("lab3");
        Label label4=new Label("lab4");
        gridPane.add(label1,0,0);
        gridPane.add(label2,0,1);
        gridPane.add(label3,1,0);
        gridPane.add(label4,1,1);
        gridPane.setPadding(new Insets(2,2,2,10));
        gridPane.setTranslateX(32);
        homePage.getChildren().add(gridPane);
    }
    @Override
    public Node getContent()
    {
        return homePage;
    }

}
