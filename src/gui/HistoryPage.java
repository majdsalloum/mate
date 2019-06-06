package gui;

import storage.StorageManger;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;

import java.util.LinkedList;

public class HistoryPage extends Page{
    VBox vBox = new VBox();
    public HistoryPage(Window window, String path, String data) {
        super(window, path, data);
        LinkedList<String> history = StorageManger.getHistory();
        if(history==null) {
            vBox.getChildren().add(new Label("No History Log"));
            return;
        }
        for (int i=0;i<history.size();i++)
        {
            Label label = new Label(history.get(i));
            label.setFont(new Font(20));
            vBox.getChildren().add(label);
            label.setOnMouseClicked((e)->{
                window.search(label.getText());
            });
            label.setOnMouseEntered((e)->{
                label.setTextFill(Color.BLUE);
            });
            label.setOnMouseExited((e)->{
                label.setTextFill(Color.BLACK);
            });
        }
    }
    @Override
    public Node getContent()
    {
        return new ScrollPane(vBox);
    }





}
