package gui;

import core.exceptions.UnSupportedSaveTypeException;
import core.render.fx.panes.DrawerPane;
import core.render.fx.panes.VBoxFlowPane;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Page {
    protected String path;
    protected Window window;
    protected String data;
    protected DrawerPane drawerPane = new VBoxFlowPane();
    public Page(Window window ,String path , String data)
    {
        this.window=window;
        this.data =data;
        this.path=path;
        window.getSearchLog().add(path);
        window.setPageIndexInSearchLog(window.getPageIndexInSearchLog()+1);
      //  window.updateTabContent();

    }
    public void setWindow(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    public DrawerPane getDrawerPane() {
        return drawerPane;
    }

    public Node getContent() {
        return new ScrollPane(drawerPane.getParent()) {{
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

    public String toBeSaved() throws UnSupportedSaveTypeException {
        return data;
    }

}
