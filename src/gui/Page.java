package gui;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Page {
    protected String path;
    protected Window window;

    protected FlowPane flowPane = new FlowPane();

    public void setFlowPane(FlowPane flowPane) {
        this.flowPane = flowPane;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Node
    getContent()
    {
       return flowPane;
    }

    public void setPath(String URL) {
        this.path = URL;
    }

    public String getPath() {
        return path;
    }
}
