package core.render;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import gui.Page;
import javafx.scene.layout.GridPane;

import java.awt.*;
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
        if (hasEffect(Effect.FONT_BOLD))
            label.setStyle("-fx-font-weight: bold;");
        if (parents.size() > 0) {
            if (parents.getLast().getDrawing_parent() == DrawerPane.DRAWING_PARENT.TABLE)
                ((GridPane) parents.getLast().getParent()).add(label, parents.getLast().col, parents.getLast().row);
        } else
            page.getFlowPane().getChildren().add(label);
    }

    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }

    private void usePane(DrawerPane drawerPane) {
        parents.add(drawerPane);
    }

    private void unUsePane() {
        DrawerPane drawerPane = parents.pollLast();
        if (parents.isEmpty())
            page.getFlowPane().getChildren().add(drawerPane.parent);
        else {
            if (parents.getLast().getDrawing_parent() == DrawerPane.DRAWING_PARENT.TABLE) {
                ((GridPane) parents.getLast().getParent()).add(drawerPane.parent, parents.getLast().col, parents.getLast().row)
                ;
            }
        }
    }

    @Override
    public void drawTable() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        gridPane.setGridLinesVisible(true);
        Parent parent = gridPane;
        DrawerPane drawerPane = new DrawerPane();
        drawerPane.setDrawing_parent(DrawerPane.DRAWING_PARENT.TABLE);
        drawerPane.setParent(parent);
        usePane(drawerPane);
    }

    @Override
    public void drawTableHeader() {
        useEffect(Effect.FONT_BOLD);
    }

    @Override
    public void endDrawTableHeader() {
        unUseEffect(Effect.FONT_BOLD);
    }

    @Override
    public void drawTableColumn() {

    }

    @Override
    public void endDraTableColumn() {
        parents.getLast().setCol(parents.getLast().getCol() + 1);
    }

    @Override
    public void drawTableRow() {
        parents.getLast().setRow(parents.getLast().getRow() + 1);
        parents.getLast().setCol(0);
    }

    @Override
    public void endDrawTableRow() {

    }

    @Override
    public void endDrawTable() {
        unUsePane();
    }

    @Override
    public void drawCaption() {
        DrawerPane drawerPane = new DrawerPane();
        drawerPane.setDrawing_parent(DrawerPane.DRAWING_PARENT.VBOX);
        usePane(drawerPane);
        useAlignment(Alignment.CENTER);
    }

    @Override
    public void endDrawCaption() {
        unUseAlignment();
    }

}