package gui;

import core.exceptions.UnSupportedSaveTypeException;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class HomePage extends Page{
    private Label matteLabel;
    private TextField textSearch;
    private Button search;
    private VBox homePage;
    public HomePage(Window window)
    {
        super(window,"matte://home","<html>Home page</html>");

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
        textSearch = new TextField("http://matebrowser.atwebpages.com/index.html");
        textSearch.setMaxWidth(500);
        search = new Button("Search");
        search.setFont(Font.font(20));
        search.setTextFill(Color.GREEN);
        homePage.getChildren().addAll(matteLabel,textSearch,search);
        search.setOnAction(event -> {
            if (textSearch.getText()!=null)
            window.search(textSearch.getText());
        });

    }
    @Override
    public Node getContent()
    {
        return homePage;
    }

    @Override
    public String toBeSaved() throws UnSupportedSaveTypeException {
        throw new UnSupportedSaveTypeException("home page");
    }
}
