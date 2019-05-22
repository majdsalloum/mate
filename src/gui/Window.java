package gui;

import Storage.StorageManger;
import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
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
import javafx.stage.FileChooser;
import network.InternetConnection;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.LinkedList;

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
       // searchLog.add("matte:\\home");


        page = new HomePage(this);
        pageToolBar = new PageToolBar(this, tabPane);

        pageToolBar.getTextSearch().setText(page.getPath());
        content = new VBox();
        createNewTab();
        tabPane.getTabs().add(tab);

        //todo add actions to buttons
    }

    public void changeContent(Page page) {
        this.page = page;
        page.setWindow(this);
    }

    public Node getContent() {
        //changeContent();
        pageToolBar.updateAppearance();
        content = new VBox();
        content.getChildren().addAll(pageToolBar.getToolBar(), page.getContent());
        return content;
    }

    public void openNewPage(String path) {
        /**send path to url connection and get page*/
        /**get page type */
        page = new HomePage(this);
        //searchLog.add(path);
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
        pageToolBar.updateAppearance();
        pageToolBar.getTextSearch().setText(page.path);
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
       // searchLog.add(path);
        showLoading();
       // searchLog.add(path);
        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.getPage(path);
    }

    public void onLoad(String string , String path) throws InvalidContentException, InvalidSyntaxException {
        page=new Page(this,path,string);
        hideLoading();
        FXDrawer fxDrawer = new FXDrawer(tab, page, ui, searchLog.getLast());
        Tag head = (Tag) HTMLParser.compile(string);
        head.draw(fxDrawer);
        updateTabContent();
    }

    public void loadPage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(ui.getMainStage());
        if (file == null) return;
        String ext = null;
        int i=file.toString().lastIndexOf(".");
        if(i==-1){}
        else ext=file.toString().substring(i);
        System.out.println(ext);
        if(ext.equals(".htm")){
            String s = file.toString();
            String data = "page not found";
            try {
                data = StorageManger.loadPage(s);
                onLoad(data,file.toString());
            } catch (Exception e) {
                page = new TextPage(this,file.toString(),data);
                updateTabContent();
            }
        }
        else if(ext.equals(".pdf"))
        {
            page = new PDFPage(this,file.toString(),"non");
            updateTabContent();
        }
        else {
            String s = file.toString();
            String data = "page cannot open ";
            try {
                data = StorageManger.loadPage(s);
            } catch (IOException e) {
            }
            if(data ==null)data = "page cannot open  ";
            page=new TextPage(this,file.toString(),data);
            updateTabContent();
        }

    }
    public void savePage()
    {
        FileChooser fileChooser =new FileChooser();
        File file = fileChooser.showSaveDialog(ui.getMainStage());
        if(file!=null)
            StorageManger.savePage(page.getData(),file.toString());

    }
    public UserInterface getUi()
    {
        return ui;
    }

    public void setPageIndexInSearchLog(Integer pageIndexInSearchLog) {
        this.pageIndexInSearchLog = pageIndexInSearchLog;
    }
}
