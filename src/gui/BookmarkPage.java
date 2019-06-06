package gui;

import Storage.StorageManger;
import core.exceptions.UnSupportedSaveType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.LinkedList;

public class BookmarkPage extends Page{
    VBox vBox = new VBox();
    public BookmarkPage(Window window, String path, String data) {
        super(window, path, data);
        LinkedList<Pair<String,String>> bookmarks = StorageManger.getBookmarks();
        if(bookmarks==null) {
            vBox.getChildren().add(new Label("No Bookmark "));
            return;
        }
        for (int i=0;i<bookmarks.size();i++)
        {
            Label label = new Label(bookmarks.get(i).getKey());
            String link = bookmarks.get(i).getValue();
            label.setFont(new Font(20));
            vBox.getChildren().add(label);
            label.setOnMouseClicked((e)->{
                window.search(link);
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

    @Override
    public String toBeSaved() throws UnSupportedSaveType {
        throw new UnSupportedSaveType("bookmark page");
    }
}


