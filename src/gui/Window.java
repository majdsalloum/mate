package gui;

import Storage.StorageManger;
import core.exceptions.InvalidContentException;
import core.exceptions.InvalidSyntaxException;
import core.parser.HTMLParser;
import core.render.fx.FXDrawer;
import core.tags.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.InternetConnection;
import tests.ParsingTest;

import java.io.File;
import java.io.IOException;
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
        StorageManger.addToHistory(path);
        if (loading > 0) return;
        // searchLog.add(path);
        showLoading();
        // searchLog.add(path);
        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.getPage(path);
    }

    public void onLoad(String string, String path) {
        (new Thread(() -> {
            page = new Page(this, path, string);
            ParsingTest.log("parsing...");
            try {
                final Tag head = HTMLParser.compile(string);
                ParsingTest.log("rendering...");
                Platform.runLater(() -> {
                    FXDrawer fxDrawer = new FXDrawer(tab, page, ui, searchLog.getLast());
                    head.draw(fxDrawer);
                    updateTabContent();
                });
            } catch (InvalidSyntaxException | InvalidContentException e) {
                this.onLoad("Error in Page", path);
            } finally {
                Platform.runLater(this::hideLoading);
            }
        })).start();

    }

    public void loadPage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(ui.getMainStage());
        if (file == null) return;
        String ext = null;
        int i = file.toString().lastIndexOf(".");
        if (i == -1) {
            ext = "txt";
        } else ext = file.toString().substring(i);
        ParsingTest.log(ext);
        showLoading();
        switch (ext) {
            case ".htm": {
                String s = file.toString();
                String data = "page not found";
                try {
                    data = StorageManger.loadPage(s);
                    onLoad(data, file.toString());
                } catch (Exception e) {
                    page = new TextPage(this, file.toString(), data);
                    updateTabContent();
                }
                break;
            }
            case ".pdf":
                page = new PDFPage(this, file.toString(), "non");
                updateTabContent();
                break;
            default: {
                String s = file.toString();
                String data = "page cannot open ";
                try {
                    data = StorageManger.loadPage(s);
                } catch (IOException e) {
                }
                if (data == null) data = "page cannot open  ";
                page = new TextPage(this, file.toString(), data);
                updateTabContent();
                break;
            }
        }

    }

    public void savePage() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(ui.getMainStage());
        if (file != null)
            StorageManger.savePage(page.getData(), file.toString());

    }

    public UserInterface getUi() {
        return ui;
    }

    public void setPageIndexInSearchLog(Integer pageIndexInSearchLog) {
        this.pageIndexInSearchLog = pageIndexInSearchLog;
    }

    public void showHistory() {
//        Window window = ui.createNewWindow();
//        window.setPage(new HistoryPage(window , "history" ,null));
//        window.updateTabContent();
//
        Window window1 = ui.createNewWindow();
        window1.setPage(new EditorModePage(window1, "editor", null));
        window1.updateTabContent();
    }

    public void showErrorMessage(String string) {
        Stage stage = new Stage();
        stage.setTitle("Error");
        Label label = new Label(string);
        Button button = new Button("Ok");
        VBox vBox = new VBox(label, button);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
        button.setOnAction((e) -> stage.close());
    }
}
