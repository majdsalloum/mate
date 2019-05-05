package gui;

import core.parser.HTMLParser;
import core.render.fx.FXDrawer;
import core.tags.*;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import network.InternetConnection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Window {
    private LinkedList<String> searchLog;
    private PageToolBar pageToolBar;
    private Page page;
    private Integer pageIndexInSearchLog = 0;
    private VBox content;
    private Tab tab;
    private UserInterface ui;

    public Window(TabPane tabPane, UserInterface ui) {
        this.ui = ui;
        searchLog = new LinkedList<>();
        searchLog.add("matte:\\home");
        pageToolBar = new PageToolBar(this, tabPane);
        //set Actions
        setActions();
        page = new HomePage();
        page.setWindow(this);
        searchLog.add(page.getPath());
        pageToolBar.getTextSearch().setText(page.getPath());
        content = new VBox();
        Integer pageCounter = 0;
        createNewTab();
        tabPane.getTabs().add(tab);
    }

    public void changeContent(Page page) {
        this.page = page;
        page.setWindow(this);
    }

    public Node getContent() {
        //changeContent();
        content = new VBox();
        content.getChildren().addAll(pageToolBar.getToolBar(), page.getContent());
        return content;
    }

    public void openNewPage(String path) {
        /**send path to url connection and get page*/
        /**get page type */
        page = new HomePage();
        searchLog.add(path);
        pageIndexInSearchLog++;

    }

    Integer getPageIndexInSearchLog() {
        return pageIndexInSearchLog;
    }

    public void createNewTab() {
        tab = new Tab("NewTab");
        Image image = Images.getImage("..\\img\\home1.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        tab.setGraphic(imageView);
        tab.closableProperty();//what is this???
        tab.setContent(this.getContent());
    }

    public void updateTabContent() {
        tab.setContent(getContent());

    }

    public LinkedList<String> getSearchLog() {
        return searchLog;
    }

    public PageToolBar getPageToolBar() {
        return pageToolBar;
    }

    public void setPageToolBar(PageToolBar pageToolBar) {
        this.pageToolBar = pageToolBar;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public void setActions() {

        pageToolBar.getSearch().setOnAction((e) -> {
            String urlPath = pageToolBar.getTextSearch().getText();
            search(urlPath);
        });

        pageToolBar.getHome().setOnAction((e) -> {
            page = new HomePage();
            page.setWindow(this);
            updateTabContent();

        });
    }

    private int loading = 0;

    public void showLoading() {
        if (loading++ > 0) {
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setPrefSize(20, 20);
            tab.setGraphic(progressIndicator);
        }
    }

    public void hideLoading() {
        if (loading == 0) return;
        if (--loading == 0) {
            Image image = Images.getImage("..\\img\\home1.png");
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(15);
            imageView.setFitWidth(15);
            tab.setGraphic(imageView);
            tab.setGraphic(imageView);
        }
    }

    public void search(String path) {
        if (loading > 0) return;
        searchLog.add(path);
        showLoading();
        page = new Page();
        page.setWindow(this);
        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.getPage(path);
    }

    public void onLoad(String string) {
        hideLoading();
        FXDrawer fxDrawer = new FXDrawer(tab, page, ui, searchLog.getLast());

        Tag head = (Tag) HTMLParser.compile(string);
        head.draw(fxDrawer);
        updateTabContent();

    }

}
