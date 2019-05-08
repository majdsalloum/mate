package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PageToolBar {
    private ToolBar toolBar;
    private Button backward, forward, refresh, home, search, newTabButton, disconnect;
    private TextField textSearch;
    private MenuItem setting, newTab, downloads, newWindow, exit , loadPage , savePage;
    private MenuBar menuBar;
    private Menu file;
    private final Integer ICON_SIZE = 15;
    Window window;
    private TabPane tabPane;

    public PageToolBar(Window window, TabPane tabPane) {
        this.window = window;
        this.tabPane = tabPane;
        toolBar = new ToolBar();
        toolBar.setPadding(new Insets(20));
        //add buttons
        backward = new Button();
        backward.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//backward.png", backward);
        backward.setAccessibleHelp("backward");


        forward = new Button();
        forward.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//forward.png", forward);
        forward.setAccessibleHelp("forward");


        refresh = new Button();
        refresh.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//refresh1.png", refresh);
        refresh.setAccessibleHelp("refresh");

        disconnect = new Button();
        disconnect.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//disconnect.png", disconnect);
        //todo: work on this

        home = new Button();
        home.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//home1.png", home);
        home.setAccessibleHelp("home");

        textSearch = new TextField();
        HBox.setHgrow(textSearch, Priority.ALWAYS);
        textSearch.setPrefHeight(30);


        search = new Button();
        search.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//search1.png", search);
        search.setAccessibleHelp("search");

        menuBar = new MenuBar();
        file = new Menu();
        ImageView imageView = new ImageView(Images.menu);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        file.setGraphic(imageView);

        newTab = new MenuItem("NewTab");
        newWindow = new MenuItem("NewWindow");
        downloads = new MenuItem("Downloads");
        savePage = new MenuItem("Save page");
        loadPage =new MenuItem("Load page");
        setting = new MenuItem("Setting");
        exit = new MenuItem("Exit");
        file.getItems().addAll(newTab, newWindow, downloads,savePage,loadPage, setting, exit);
        menuBar.getMenus().addAll(file);

        newTabButton = new Button();
        insertImage("..\\img//newtab.png", newTabButton);
        newTabButton.setAccessibleHelp("newTabButton");

        toolBar.getItems().add(new Separator());
        toolBar.getItems().addAll(backward, forward, refresh, home, textSearch, search, newTabButton, menuBar);
        updateAppearance();
        setActions();
    }

    private void insertImage(String imagePath, Button button) {
        Image image = Images.getImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        button.setGraphic(imageView);
    }

    ///Getters/////////////////////////////////////
    public ToolBar getToolBar() {
        return toolBar;
    }

    public Button getBackward() {
        return backward;
    }

    public Button getForward() {
        return forward;
    }

    public Button getRefresh() {
        return refresh;
    }

    public Button getHome() {
        return home;
    }

    public Button getSearch() {
        return search;
    }

    public Button getNewTabButton() {
        return newTabButton;
    }

    public TextField getTextSearch() {
        return textSearch;
    }

    public MenuItem getSetting() {
        return setting;
    }

    public MenuItem getNewTab() {
        return newTab;
    }

    public MenuItem getDownloads() {
        return downloads;
    }

    public MenuItem getNewWindow() {
        return newWindow;
    }

    public MenuItem getExit() {
        return exit;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public Menu getFile() {
        return file;
    }

    void setWindow(Window window) {
        this.window = window;
    }

    public MenuItem getLoadPage() { return loadPage;}

    public MenuItem getSavePage() { return savePage; }
    public void updateAppearance()
    {
        if (window.getPageIndexInSearchLog() == window.getSearchLog().size()-1)
            forward.setDisable(true);
        else forward.setDisable(false);

        if(window.getPageIndexInSearchLog() ==0 )
            backward.setDisable(true);
        else backward.setDisable(false);

    }

    private void setActions()

    {
        //todo : replace to set on right click
        savePage.setOnAction((e)->
        {
            window.savePage();
        });
        loadPage.setOnAction((e)->{
            window.loadPageInNewTab();
        });
        newTabButton.setOnAction((e)->
        {
            window.getUi().createNewWindow();
        });
        newTab.setOnAction((e)->{
            window.getUi().createNewWindow();
        });
        search.setOnAction((e) -> {
            String urlPath = textSearch.getText();
            window.search(urlPath);
        });
        home.setOnAction((e)->
        {
            window.setPage(new HomePage(window));
            window.updateTabContent();
        });
        refresh.setOnAction((e)->{
            //todo: reload data from internet
            //todo :make it in new page
            window.updateTabContent();
        });
        exit.setOnAction((e)->{
            window.getUi().getMainStage().close();
        });
        forward.setOnAction((e)->{
        });

        backward.setOnAction((e)->{
            window.setPageIndexInSearchLog(window.getPageIndexInSearchLog()-1);
            window.search(window.getSearchLog().get(window.getPageIndexInSearchLog()));
        });

        //todo: make sure if this correct
        textSearch.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    search.getOnAction().handle(new ActionEvent());
                }
            }
        });
    }
}
