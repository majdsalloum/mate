package core.render;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import gui.Page;



public class FXDrawer implements Drawer {
    Tab tab;
    Page page;

    public FXDrawer(Tab tab, Page page) {
        this.tab = tab;
        this.page = page;
    }

    @Override
    public void drawText(String text) {
        Label label = new Label(text);
        label.setText(text);
        page.getFlowPane().getChildren().add(label);
    }

    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }

}