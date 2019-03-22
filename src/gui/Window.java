package gui;

import core.render.FXDrawer;
import core.tags.HTML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import network.InternetConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Window {
    private List<String> searchLog;
    private PageToolBar pageToolBar;
    private Page page;
    private Integer pageIndexInSearchLog = 0;
    private VBox content;
    private Tab tab;
    private FXDrawer drawer;

    private void onLoad(HTML tag) {
        tag.draw(drawer);
    }

    public Window(TabPane tabPane) {

        searchLog = new ArrayList<>();
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
        drawer = new FXDrawer(tab, page);
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
        Image image = new Image(getClass().getResourceAsStream("..\\img\\home1.png"));
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


    public List<String> getSearchLog() {
        return searchLog;
    }

    public void setSearchLog(List<String> searchLog) {
        this.searchLog = searchLog;
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
            InputStream inputStream = null;
            if (urlPath != null) {
                //check if url is external or internal
                InternetConnection internetConnection = new InternetConnection();
                try {
                    inputStream = internetConnection.getPage(urlPath);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            Page newPage = new TestPage(inputStream);
            page = newPage;
            updateTabContent();

        });

        pageToolBar.getHome().setOnAction((e) -> {
            page = new HomePage();
            page.setWindow(this);
            updateTabContent();

        });
    }

    public void search(String path) {
        InternetConnection internetConnection = new InternetConnection();
        InputStream inputStream = null;
        try {
            inputStream = internetConnection.getPage(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Page newPage = new TestPage(inputStream);
        page = newPage;
        page.setWindow(this);
        updateTabContent();
    }

}
