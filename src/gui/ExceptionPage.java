package gui;

import core.exceptions.UnSupportedSaveType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class ExceptionPage extends Page {
    private Exception exception;


    ExceptionPage(Window window, String path, String data, Exception exception) {
        super(window, path, data);
        this.exception = exception;
    }


    @Override
    public Node getContent() {
        final BorderPane root = new BorderPane();
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#FA111199"), null, null)));
        hBox.setPadding(new Insets(50));
        hBox.setAlignment(Pos.CENTER);
        root.setCenter(hBox);
        hBox.setFillHeight(true);
        hBox.getChildren().add(
                new ImageView(Images.getImage("..\\img\\mate_broken.png"))
        );
        VBox vBox = new VBox();
        vBox.setSpacing(25);
        vBox.getChildren().addAll(
                new Label("Your mate just 'maslet'") {{
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.5f), null, null)));
                    this.setStyle("-fx-font-weight: bold; -fx-font-size: 24px;");
                }},
                new Label(exception.toString()) {{
                    this.setBackground(new Background(new BackgroundFill(Color.rgb(255, 0, 0, 0.5f), null, null)));
                    this.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #FFF;");
                    this.setWrapText(true);
                }}
        );

        hBox.getChildren().add(vBox);
        return root;
    }

    @Override
    public String toBeSaved() throws UnSupportedSaveType {
        throw new UnSupportedSaveType("Exception page");
    }
}
