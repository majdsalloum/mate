import Network.InternetConnection;
import core.exceptions.UnsupportedAttributeException;
import core.render.FXDrawer;
import core.tags.HTML;
import core.tags.Tag;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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
        Hyperlink hyperlink = new Hyperlink("welcome");
        textSearch = new TextField();
        textSearch.setMaxWidth(500);
        search = new Button("Search");
        search.setFont(Font.font(20));
        search.setTextFill(Color.GREEN);
        homePage.getChildren().addAll(matteLabel,textSearch,search);
        search.setOnAction(event -> {
            Window.search(textSearch.getText());
        });

        Tag node = new HTML();
        node.draw(new FXDrawer(matteLabel));

    }
    @Override
    public Node getContent()
    {
        return homePage;
    }

    public Button getSearch() {
        return search;
    }

    public TextField getTextSearch() {
        return textSearch;
    }
}
