package core.render.fx;

import core.render.Alignment;
import core.render.Drawer;
import core.render.Effect;
import core.render.actions.Action;
import core.render.actions.HrefAction;
import core.render.fx.panes.*;
import gui.Page;
import gui.UserInterface;
import gui.Window;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;


public class FXDrawer extends Drawer {
    Tab tab;
    Page page;
    UserInterface ui;
    LinkedList<DrawerPane> parents = new LinkedList<>();

    public FXDrawer(Tab tab, Page page, UserInterface userInterface, String baseUrl) {
        super(baseUrl);
        this.tab = tab;
        this.page = page;
        this.ui = userInterface;
    }

    private void drawNode(Node node) {
        if (parents.size() > 0) {
            parents.getLast().add(node);
        } else
            page.getFlowPane().getChildren().add(node);
    }

    @Override
    public void drawText(String text) {
        Label label = new Label(text);
        label.setText(text);
        if (hasEffect(Effect.FONT_BOLD))
            label.setStyle("-fx-font-weight: bold;");
        if (hasAction(HrefAction.class)) {
            label.setStyle(label.getStyle() + "-fx-font-style: italic;");
            Styler.changeColorToPassiveLink(label);
            label.setOnMouseEntered(Styler::changeColorToActiveLink);
            label.setOnMouseExited(Styler::changeColorToPassiveLink);
        }
        drawNode(label);
    }

    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }

    @Override
    public void drawImage(String path) {
        final ImageView iv = new ImageView();
        drawNode(iv);
        page.getWindow().showLoading();
        (new Thread(() -> {
            Image image = new Image(getRelativePath(path));
            Platform.runLater(() -> {
                iv.setImage(image);
                page.getWindow().hideLoading();
            });
        }
        )).start();
    }

    private void usePane(DrawerPane drawerPane) {
        parents.add(drawerPane);
    }

    private void unUsePane() {
        DrawerPane drawerPane = parents.pollLast();
        drawNode(drawerPane.getParent());
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
        ((GridDrawerPane) parents.getLast()).setCol(((GridDrawerPane) parents.getLast()).getCol() + 1);

    }

    @Override
    public void endDraTableColumn() {
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
    public void drawUnOrderedList(String symbol) {
        VBox vBox = new VBox();
        vBox.setTranslateX(32);
        UnOrderedListDrawPane listDrawPane = new UnOrderedListDrawPane(vBox, symbol);
        usePane(listDrawPane);
    }
    @Override
    public void drawOrderedList(String start , String symbol) {
        VBox vBox = new VBox();
        vBox.setTranslateX(32);
        OrderedListDrawPane listDrawPane = new OrderedListDrawPane(vBox,Integer.parseInt(start),symbol);
        usePane(listDrawPane);
    }

    @Override
    public void endDrawOrderedList() {
        unUsePane();
    }

    @Override
    public void endDrawUnOrderedList() {
        unUsePane();
    }

    @Override
    public void drawListItem() {
        HBox hBox = new HBox();
        hBox.setSpacing(4);
        String string = "";
        if (parents.getLast().getClass().equals(UnOrderedListDrawPane.class) )
            string = ((UnOrderedListDrawPane) parents.getLast()).getSymbol().getNext();
        else if(parents.getLast().getClass().equals(OrderedListDrawPane.class))
            string = ((OrderedListDrawPane) parents.getLast()).getSymbol().getNext();
        hBox.getChildren().add(new Label(string));
        ListItem listItem = new ListItem(hBox);
        usePane(listItem);
    }

    @Override
    public void endDrawListItem() {
        unUsePane();
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
        drawerPane.getParent().setCursor(Cursor.HAND);
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
    public void unUseAction() {
        super.unUseAction();
        unUsePane();
    }

}