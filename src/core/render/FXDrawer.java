package core.render;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import gui.Page;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;


public class FXDrawer extends Drawer {
    Tab tab;
    Page page;
    LinkedList<DrawerPane> parents = new LinkedList<>();

    public FXDrawer(Tab tab, Page page) {
        this.tab = tab;
        this.page = page;
    }

    @Override
    public void drawText(String text) {
        Label label = new Label(text);
        label.setText(text);
        if (parents.size() > 0) {
            if (parents.peek().getDrawing_parent() == DrawerPane.DRAWING_PARENT.TABLE)
                ((GridPane) parents.peek().getParent()).add(label, parents.peek().col, parents.peek().row);
        } else
            page.getFlowPane().getChildren().add(label);
    }

    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }

    @Override
    public void usePane(DrawerPane drawerPane) {
        parents.add(drawerPane);
    }

    @Override
    public void unUsePane() {
        DrawerPane drawerPane = parents.pop();
        if (parents.isEmpty())
            page.getFlowPane().getChildren().add(drawerPane.parent);
        else {
            if (parents.peek().getDrawing_parent() == DrawerPane.DRAWING_PARENT.TABLE) {
                ((GridPane) parents.peek().getParent()).add(drawerPane.parent, drawerPane.col, drawerPane.row);
            }
        }
    }



    @Override
    public LinkedList<DrawerPane> getParents() {
        return parents;
    }
}