package gui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class TextPage extends Page{
    FlowPane flowPane;
    public TextPage(Window window, String path, String data) {
        super(window, path, data);
        flowPane = new FlowPane(new Text("Page viewed as text \n"+data)) ;
        flowPane.setPrefSize(250,750);
    }

    @Override
    public Node getContent() {
        return new ScrollPane(flowPane);
    }
}
