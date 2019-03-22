package core.render;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import gui.Page;
public class FXDrawer implements Drawer {
    Tab tab;
    Page page;

    public FXDrawer(Label label) {
//        this.label = label;
    }

    @Override
    public void drawText(String text) {
//        label.setText(text);
    }

    // TODO
    @Override
    public void setTitle(String text) {

    }
}
