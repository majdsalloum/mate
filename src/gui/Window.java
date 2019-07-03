package gui;

import Storage.StorageManger;
import core.exceptions.UnSupportedSaveTypeException;
import core.render.Drawer;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.InternetConnection;
import tests.ParsingTest;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Window {
    private LinkedList<String> searchLog;
    private PageToolBar pageToolBar;
    private Page page;
    private Integer pageIndexInSearchLog = 0;
    private VBox content;
    private Tab tab;
    private UserInterface ui;
    private Tag root;
    private Drawer drawer;

    public void drawDOM() {
        if (root == null || drawer == null) {
            // TODO DISPLAY PROPER ERROR CAUSE PAGE ISNT RENDERED YET
            return;
        }
        drawer.drawDOM(root);
    }
    public Window(TabPane tabPane, UserInterface ui) {
        this.ui = ui;
        searchLog = new LinkedList<>();
        searchLog.add("matte://home");


        page = new HomePage(this);
        pageToolBar = new PageToolBar(this, tabPane);

        pageToolBar.getTextSearch().setText(page.getPath());
        content = new VBox();
        createNewTab();
        tabPane.getTabs().add(tab);
        //todo add actions to buttons

       // Deubug();
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
    public void setPageAndUpdate(Page page) {
        this.page = page;
        updateTabContent();
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    private int loading = 0;

    public void showLoading() {
        if (++loading > 0) {
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
        }
    }

    public void search(String path) {
        if (path.equals("matte://home"))
        {
            setPageAndUpdate(new HomePage(this));
            return;
        }
        StorageManger.addToHistory(path);
        if (loading > 0) return;
        // searchLog.add(path);
        showLoading();
        pageIndexInSearchLog = searchLog.size();

        searchLog.add(path);

        // searchLog.add(path);
        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.getPage(path);
    }

    public void onLoad(String string, String path) {
        (new Thread(() -> {
            page = new Page(this, path, string);
            ParsingTest.log("parsing...");
            try {
                root = HTMLParser.compile(string);
                ParsingTest.log("rendering...");
                Platform.runLater(() -> {
                    drawer = new FXDrawer(tab, page, ui, searchLog.isEmpty()?path:searchLog.getLast());
                    root.draw(drawer);
                    updateTabContent();
                });
            } catch (InvalidSyntaxException | InvalidContentException e) {
                e.printStackTrace();
                page = new ExceptionPage(this,path,string,e);
                root = null;
                drawer = null;
                Platform.runLater(this::updateTabContent);
            } finally {
                Platform.runLater(this::hideLoading);
            }
        })).start();
        pageToolBar.getTextSearch().setText(page.path);


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
        if(ext.equals(".html"))
            ext=new String(".htm");
        switch (ext) {
            case ".htm": {
                String s = file.toString();
                String data = "page not found";
                try {
                    data = StorageManger.loadPage(s);
                    ParsingTest.log(data);
                    onLoad(data, file.toString());
                } catch (IOException e) {
                    page = new TextPage(this, file.toString(), data);
                    updateTabContent();
                    hideLoading();
                }
                break;
            }
            case ".pdf":
                page = new PDFPage(this, file.toString(), "non");
                updateTabContent();
                hideLoading();
                break;
            default:
                String s = file.toString();
                String data = "page cannot open ";
                try {
                    data = StorageManger.loadPage(s);
                } catch (IOException e) {
                }
                if (data == null) data = "page cannot open  ";
                page = new TextPage(this, file.toString(), data);
                updateTabContent();
                hideLoading();
                break;
        }

    }

    public void savePage() {
        FileChooser fileChooser = new FileChooser();
        String data;
        try {
             data =  page.toBeSaved();
            File file = fileChooser.showSaveDialog(ui.getMainStage());
            if (file != null)
                StorageManger.savePage(data, file.toString());
        } catch (UnSupportedSaveTypeException unSupportedSaveTypeException) {
            showErrorMessage("Error in saving" , "this type cannot be saved" , unSupportedSaveTypeException.getMessage());
        }

    }

    public UserInterface getUi() {
        return ui;
    }

    public void setPageIndexInSearchLog(Integer pageIndexInSearchLog) {
        this.pageIndexInSearchLog = pageIndexInSearchLog;
    }

    public void showHistory() {
        Window window = ui.createNewWindow();
        window.setPage(new HistoryPage(window , "history" ,null));
        window.updateTabContent();

    }


    public void showErrorMessage(String title ,String header , String content )
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add((Images.getImage("..\\img\\failed.png")));
        alert.show();

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

    public void markPage() {
        Stage stage = new Stage();
        stage.setTitle("bookmark");
        Label label = new Label("type the title :");
        TextField textField = new TextField();
        Button button = new Button("Done");
        button.setOnAction((e)->{
            stage.close();
            StorageManger.addToBookmarks(textField.getText()!=null?textField.getText():page.path ,page.path);
        });
        stage.setScene(new Scene(new VBox(label,textField,button)));
        stage.show();
    }

    public void errorInGettingPage(String path)
    {
        Stage stage = new Stage();
        stage.getIcons().add(Images.logo);
        stage.setTitle("Error in loading page");
        Label label = new Label("Error happened in loading page \n" +
                "press ok to play mate game ");
        Button ok=new Button("Ok");
        Button no=new Button("No ,thanks");
        HBox hBox = new HBox(ok,no);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox= new VBox(label,hBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(500,200);
        stage.setScene(new Scene(vBox));
        stage.show();
        hideLoading();
        ok.setOnAction((e)->{
            stage.close();
            setPageAndUpdate(new GamePage(this,path,""));
        });
        no.setOnAction((e)-> stage.close());
    }
    public void searchOld(String path)
    {
        if (path.equals("matte://home"))
        {
            setPageAndUpdate(new HomePage(this));
            return;
        }
        StorageManger.addToHistory(path);
        if (loading > 0) return;
        // searchLog.add(path);
        showLoading();
        // searchLog.add(path);
        searchLog.add(path);
        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.getPage(path);

    }

    private void Deubug()
    {
        Stage stage = new Stage();
        Button get = new Button("get");
        VBox vBox = new VBox();
        Label label = new Label();
        get.setOnAction((e)->{
            vBox.getChildren().setAll();
            for (String aSearchLog : searchLog)
            {
                vBox.getChildren().add(new Label(aSearchLog));
                label.setText(pageIndexInSearchLog+"");
            }
        });
        stage.setScene(new Scene(new ScrollPane(new VBox(get , vBox , label))));
        stage.show();
    }
}
