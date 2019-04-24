package gui;

import core.exceptions.InvalidContentException;
import core.parser.HTMLParser;
import core.render.FXDrawer;
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
import java.util.List;

public class Window {
    private List<String> searchLog;
    private PageToolBar pageToolBar;
    private Page page;
    private Integer pageIndexInSearchLog = 0;
    private VBox content;
    private Tab tab;

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
            search(urlPath);
        });

        pageToolBar.getHome().setOnAction((e) -> {
            page = new HomePage();
            page.setWindow(this);
            updateTabContent();

        });
    }

    public void search(String path) {
        //TODO: HANDLE SEARCH IF PREVIOUS SEARCH STILL RUNNING
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setPrefSize(20,20);
        tab.setGraphic(progressIndicator);
        page = new Page();
        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.getPage(path);
    }

    public void onLoad(String string) {
        Image image = new Image(getClass().getResourceAsStream("..\\img\\home1.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        tab.setGraphic(imageView);
        tab.setGraphic(imageView);
        FXDrawer fxDrawer = new FXDrawer(tab, page);
        Tag head = HTMLParser.compile(string);
        head=new HTML();
        BODY body = new BODY();
        TABLE table = new TABLE();
        TR tr=new TR();
        TD td=new TD();
        TD td1=new TD();
        TR tr1=new TR();
        TD td2=new TD();
        try {
            head.addChildren(body);

        body.addChildren(table);
        table.addChildren(tr);
        tr.addChildren(td);
        td.addChildren("hello");
        tr.addChildren(td1);
        td1.addChildren("bye");
        table.addChildren(tr1);
        tr1.addChildren(td2);
        td2.addChildren("ok");
        } catch (InvalidContentException e) {
            e.printStackTrace();
        }
        head.draw(fxDrawer);
        updateTabContent();

    }

}
