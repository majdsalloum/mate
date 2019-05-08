package gui;

import javafx.geometry.Insets;
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
    protected String data;
    protected FlowPane flowPane = new FlowPane();
    public Page(Window window)
    {
        this.window=window;
        flowPane.setHgap(2);
        flowPane.setVgap(2);
    }
    public void setWindow(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Node getContent() {
        int x;
        return new ScrollPane(flowPane) {{
            VBox.setVgrow(this, Priority.ALWAYS);
            this.setFitToHeight(true);
            this.setFitToWidth(true);
        }};
    }

    public void setPath(String URL) {
        this.path = URL;
    }
    public void setData(String data){
        this.data=data;
    }

    public String getData() {
        return data;
    }

    public String getPath() {
        return path;
    }
}
