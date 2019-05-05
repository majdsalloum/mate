package gui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Page {
    protected String path;
    protected Window window;

    protected FlowPane flowPane = new FlowPane();

    public void setWindow(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Node
    getContent() {
        return new ScrollPane(flowPane) {{
            VBox.setVgrow(this, Priority.ALWAYS);
            this.setFitToHeight(true);
        }};
    }

    public void setPath(String URL) {
        this.path = URL;
    }

    public String getPath() {
        return path;
    }
}
