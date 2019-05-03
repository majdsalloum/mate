package core.render.fx;

import core.render.Alignment;
import core.render.Drawer;
import core.render.Effect;
import core.render.actions.Action;
import core.render.actions.HrefAction;
import core.render.fx.panes.DrawerPane;
import core.render.fx.panes.GridDrawerPane;
import gui.UserInterface;
import gui.Window;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import gui.Page;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;


public class FXDrawer extends Drawer {
    Tab tab;
    Page page;
    UserInterface ui;
    LinkedList<DrawerPane> parents = new LinkedList<>();

    public FXDrawer(Tab tab, Page page, UserInterface userInterface) {
        this.tab = tab;
        this.page = page;
        this.ui = userInterface;
    }

    @Override
    public void drawText(String text) {
        Label label = new Label(text);
        label.setText(text);
        if (hasEffect(Effect.FONT_BOLD))
            label.setStyle("-fx-font-weight: bold;");
        if (parents.size() > 0) {
            parents.getLast().add(label);
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
            page.getFlowPane().getChildren().add(drawerPane.getParent());
        else {
            parents.getLast().add(drawerPane.getParent());
        }
    }

    @Override
    public void drawTable() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(2);
        gridPane.setHgap(2);
        gridPane.setGridLinesVisible(true);
        usePane(new GridDrawerPane(gridPane));
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
        ((GridDrawerPane) parents.getLast()).setCol(((GridDrawerPane) parents.getLast()).getCol() + 1);
    }

    @Override
    public void drawTableRow() {
        GridDrawerPane p = (GridDrawerPane) parents.getLast();
        p.setRow(p.getRow() + 1);
        p.setCol(0);
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
        DrawerPane drawerPane = new DrawerPane(new FlowPane());
        usePane(drawerPane);
        useAlignment(Alignment.CENTER);
    }

    @Override
    public void endDrawCaption() {
        unUseAlignment();
    }

    @Override
    public void useAction(Action action) {
        super.useAction(action);
        DrawerPane drawerPane = new DrawerPane(new FlowPane());
        drawerPane.getParent().setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), null, null)));
        if (action instanceof HrefAction) {
            HrefAction hrefAction = (HrefAction) action;
            drawerPane.getParent().setOnMouseClicked((event) -> {
                Window window = this.ui.createNewWindow();
                window.search(hrefAction.getLink());
            });
        }
        parents.addLast(drawerPane);
    }

    @Override
    public void unUssAction() {
        super.unUssAction();
        unUsePane();
    }
}