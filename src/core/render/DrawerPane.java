package core.render;

import javafx.scene.Parent;

public class DrawerPane {
    public enum DRAWING_PARENT {
        TABLE, VBOX
    }

    Integer row = -1, col = -1;
    Parent parent;
    DRAWING_PARENT drawing_parent;

    public DrawerPane() {

    }

    public DrawerPane(Integer row, Integer col, Parent parent, DRAWING_PARENT drawing_parent) {
        this.row = row;
        this.col = col;
        this.parent = parent;
        this.drawing_parent = drawing_parent;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public DRAWING_PARENT getDrawing_parent() {
        return drawing_parent;
    }

    public void setDrawing_parent(DRAWING_PARENT drawing_parent) {
        this.drawing_parent = drawing_parent;
    }


    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    public void setRow(Integer val) {
        row = val;
    }

    public void setCol(Integer val) {
        col = val;
    }
}
