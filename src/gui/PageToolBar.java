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
    private Button backward, forward, refresh, home, search, newTabButton, disconnect , bookmark;
    private TextField textSearch;
    private MenuItem setting, newTab, download, newWindow, exit , loadPage , savePage , HTMLEditor , bookmarks , history;
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
        backward.setTooltip(new Tooltip("backward"));


        forward = new Button();
        forward.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//forward.png", forward);
        forward.setAccessibleHelp("forward");
        forward.setTooltip(new Tooltip("forward"));

        refresh = new Button();
        refresh.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//refresh1.png", refresh);
        refresh.setAccessibleHelp("refresh");
        refresh.setTooltip(new Tooltip("refresh"));

        disconnect = new Button();
        disconnect.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//disconnect.png", disconnect);
        disconnect.setTooltip(new Tooltip("disconnect"));
        //todo: work on this

        home = new Button();
        home.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//home1.png", home);
        home.setAccessibleHelp("home");
        home.setTooltip(new Tooltip("home"));

        textSearch = new TextField();
        HBox.setHgrow(textSearch, Priority.ALWAYS);
        textSearch.setPrefHeight(30);


        search = new Button();
        search.setPrefSize(ICON_SIZE, ICON_SIZE);
        insertImage("..\\img//search1.png", search);
        search.setAccessibleHelp("search");
        search.setTooltip(new Tooltip("search"));

        bookmark = new Button() ;
        bookmark.setPrefSize(ICON_SIZE , ICON_SIZE);
        insertImage("..\\img\\bookmark.png" , bookmark);
        bookmark.setTooltip(new Tooltip("bookmark"));

        newTabButton = new Button();
        insertImage("..\\img//newtab.png", newTabButton);
        newTabButton.setAccessibleHelp("newTabButton");
        newTabButton.setTooltip(new Tooltip("new tab"));

        //menu stuff
        menuBar = new MenuBar();
        file = new Menu();
        ImageView imageView = new ImageView(Images.menu);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        file.setGraphic(imageView);

        //menu items

        newTab = new MenuItem("NewTab");
        newWindow = new MenuItem("NewWindow");
        download = new MenuItem("Download");
        savePage = new MenuItem("Save page");
        loadPage =new MenuItem("Load page");
        setting = new MenuItem("Setting");
        exit = new MenuItem("Exit");
        bookmarks = new MenuItem("Bookmarks");
        HTMLEditor = new MenuItem("HTML editor");
        history = new MenuItem("history");
        file.getItems().addAll(newTab, newWindow, download,bookmarks,HTMLEditor,history,savePage,loadPage, setting, exit);
        menuBar.getMenus().addAll(file);

        //add all
        toolBar.getItems().add(new Separator());
        toolBar.getItems().addAll(backward, forward, refresh, home, textSearch, search,bookmark, newTabButton, menuBar);
        setActions();
        updateAppearance();

    }

    private void insertImage(String imagePath, Button button) {
        Image image = Images.getImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        button.setGraphic(imageView);
    }

    public void updateAppearance()
    {
        if (window.getPageIndexInSearchLog() == window.getSearchLog().size()-1)
            forward.setDisable(true);
        else forward.setDisable(false);

        if(window.getPageIndexInSearchLog() ==0 )
            backward.setDisable(true);
        else backward.setDisable(false);

        if (window.getPage() instanceof Page)
            savePage.setDisable(false);
        if(window.getPage() instanceof HomePage)
            savePage.setDisable(true);
        if (window.getPage() instanceof TextPage)
            savePage.setDisable(true);
        if (window.getPage() instanceof PDFPage)
            savePage.setDisable(true);


        textSearch.setText(window.getPage().path);
    }

    private void setActions()

    {
        //todo : replace to set on right click
        savePage.setOnAction((e)->
        {
            if(!savePage.isDisable())
            window.savePage();
        });
        loadPage.setOnAction((e)->{
            window.loadPage();
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

        download.setOnAction((e)->{
            DownloadWindow downloadWindow = new DownloadWindow();
            downloadWindow.showDownloadWindow();
        });

        bookmark.setOnAction((e->{
            //todo
        }));
        HTMLEditor.setOnAction((e)->{
            window.setPageAndUpdate(new EditorModePage(window , "editor" ,null));
        });
        history.setOnAction((e)->{
            window.setPageAndUpdate(new HistoryPage(window , "history" ,null));
        });
    }

    public ToolBar getToolBar() {
        return toolBar;
    }
    public TextField getTextSearch(){return textSearch;}
    public void setWindow(Window window) {
        this.window = window;
    }
}
