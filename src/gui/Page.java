package gui;

import javafx.scene.Node;

public class Page {
    protected String path;
    protected Window window;


    public void setWindow(Window window) {
        this.window = window;
    }

    public Node getContent()
    {
       return null;
    }

    public void setPath(String URL) {
        this.path = URL;
    }

    public String getPath() {
        return path;
    }


}
