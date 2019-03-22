package GUI;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PageToolBar {
    private ToolBar toolBar;
    private Button backward,forward,refresh,home,search,newTabButton;
    private TextField textSearch ;
    private MenuItem setting,newTab,downloads,newWindow,exit;
    private MenuBar menuBar;
    private Menu file;
    private final Integer ICON_SIZE=15;
    Window window;
    private TabPane tabPane;

    public PageToolBar(Window window , TabPane tabPane)
    {
        this.window=window;
        this.tabPane=tabPane;
        toolBar = new ToolBar();
        toolBar.setPadding(new Insets(20));
        //add buttons
        backward =new Button();
        backward.setPrefSize(ICON_SIZE,ICON_SIZE);
        insertImage("..\\Img//backward.png",backward);
        backward.setAccessibleHelp("backward");
//        if (window.getSearchLog().size()<=1)
//            backward.setDisable(true);
//        else backward.setDisable(false);

        forward =new Button();
        forward.setPrefSize(ICON_SIZE,ICON_SIZE);
        insertImage("..\\Img//forward.png",forward);
        forward.setAccessibleHelp("forward");
//        if (window.getPageIndexInSearchLog()==window.getSearchLog().size()-1)
//            forward.setDisable(true);
//        else forward.setDisable(false);

        refresh =new Button();
        refresh.setPrefSize(ICON_SIZE,ICON_SIZE);
        insertImage("..\\Img//refresh1.png",refresh);
        refresh.setAccessibleHelp("refresh");

        home=new Button();
        home.setPrefSize(ICON_SIZE,ICON_SIZE);
        insertImage("..\\Img//home1.png",home);
        home.setAccessibleHelp("home");

        textSearch =new TextField();
        textSearch.setPrefWidth(1200-(ICON_SIZE*6));//todo :Edit this
        textSearch.setPrefHeight(30);

        search =new Button();
        search.setPrefSize(ICON_SIZE,ICON_SIZE);
        insertImage("..\\Img//search1.png",search);
        search.setAccessibleHelp("search");

        menuBar=new MenuBar();
        file =new Menu();
        Image image=new Image(getClass().getResourceAsStream("..\\Img//menu.png"));
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(ICON_SIZE);
        imageView.setFitWidth(ICON_SIZE);
        file.setGraphic(imageView);

        newTab=new MenuItem("NewTab");
        newWindow=new MenuItem("NewWindow");
        downloads=new MenuItem("Downloads");
        setting=new MenuItem("Setting");
        exit=new MenuItem("Exit");
        file.getItems().addAll(newTab,newWindow,downloads,setting,exit);
        menuBar.getMenus().addAll(file);

        newTabButton= new Button();
        insertImage("..\\Img//newtab.png",newTabButton);
        newTabButton.setAccessibleHelp("newTabButton");

        toolBar.getItems().add(new Separator());
        toolBar.getItems().addAll(backward,forward,refresh,home,textSearch,search,newTabButton,menuBar);
    }

    private void insertImage(String imagePath,Button button)
    {
        Image image=new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView=new ImageView(image);
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
    void setWindow(Window window)
    {
        this.window=window;
    }
}
