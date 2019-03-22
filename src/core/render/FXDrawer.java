package core.render;

import javafx.scene.control.Label;

public class FXDrawer implements Drawer {
    Label label;
    public FXDrawer(Label label) {
        this.label = label;
    }

    @Override
    public void drawText(String text) {
        label.setText(text);
    }
}
