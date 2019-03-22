package gui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class Page {
    protected String path;
    protected Window window;
    protected FlowPane flowPane = new FlowPane();

    public void setWindow(Window window) {
        this.window = window;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Node getContent() {
        return new ScrollPane(flowPane);
    }

    public void setPath(String URL) {
        this.path = URL;
    }

    public String getPath() {
        return path;
    }


}
