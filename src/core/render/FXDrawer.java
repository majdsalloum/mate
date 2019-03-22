package core.render;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import gui.Page;
import javafx.scene.layout.GridPane;

public class FXDrawer implements Drawer {
    Tab tab;
    Page page;
    GridPane gridPane;
    private int column = 0;
    private int row = 0;
    private static final Integer MAX_COLUMNS = 12;

    public FXDrawer(Tab tab, Page page) {
        this.tab = tab;
        this.page = page;
    }

    @Override
    public void drawText(String text) {
        Label label = new Label(text);
        label.setText(text);
    }

    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }
}