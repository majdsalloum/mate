package core.render.fx.panes;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GridDrawerPane extends DrawerPane {
    Integer row = -1, col = -1;

    public GridDrawerPane() {
        super(new GridPane());
    }

    public GridDrawerPane(GridPane p) {
        super(p);
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

    @Override
    public void add(Node node) {
        ((GridPane) parent).add(node, col, row);
    }
}
