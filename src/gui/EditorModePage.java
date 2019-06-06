package gui;


import core.parser.HTMLParser;
import core.render.Drawer;
import core.render.fx.FXDrawer;
import core.tags.Tag;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.*;

public class EditorModePage extends Page{
    public enum MOD
    {
        ON_TIME , ON_EDIT ,NON
    }
    public enum Style
    {
        DEFAULT , BLACK , DARKGRAY
    }
    Style style = Style.DEFAULT;
    String defualtTextAreaStyle;
    HBox hBox = new HBox();
    MOD mod = MOD.NON;
    TextArea textArea;
    Node result  = new Label("NON");
    VBox editSide ;

    public EditorModePage(Window window, String path, String data) {
        super(window, path, data);
        createEditArea();
    }

    private void createEditArea()
    {
        ToolBar toolBar = new ToolBar();
        Menu styleMenu = new Menu("Style");
        MenuItem defualt = new MenuItem("Default");
        MenuItem darkGray = new MenuItem("Dark gray");
        MenuItem black = new MenuItem("Black");
        styleMenu.getItems().addAll(defualt,darkGray,black);

        Menu modMenu = new Menu("Mod");
        MenuItem non = new MenuItem("NON");
        MenuItem automatic = new MenuItem("automatic");
        MenuItem onEdit = new MenuItem("On Edit");
        modMenu.getItems().addAll(non,automatic,onEdit);

        Button compile = new Button("Compile");
        MenuBar menuBar = new MenuBar(modMenu , styleMenu);
        toolBar.getItems().addAll(menuBar , compile);


        textArea = new TextArea();
        textArea.setPrefHeight(100000);
        hBox.getChildren().add(textArea);
        setStyle(Style.DEFAULT);

        editSide = new VBox(toolBar , textArea);
        editSide.setPrefSize(Stage.getWindows().get(0).getWidth()/2 , Stage.getWindows().get(0).getHeight()*5);

        /**Actions*/
        textArea.setOnKeyTyped((e)->{
            if(mod.equals(MOD.ON_EDIT))
            updateViewSide();
        });

        compile.setOnAction((e)->{
            updateViewSide();
        });

        defualt.setOnAction((e)->{setStyle(Style.DEFAULT);});
        darkGray.setOnAction((e)->{setStyle(Style.DARKGRAY);});
        black.setOnAction((e)->{setStyle(Style.BLACK);});

        non.setOnAction((e)->{
            setMod(MOD.NON);
            disActiveTimer();
        });
        automatic.setOnAction((e)->{
            setMod(MOD.ON_TIME);
            activeTimer();
        });
        onEdit.setOnAction((e)->{
            disActiveTimer();
            setMod(MOD.ON_EDIT);
        });

    }
    private void updateViewSide()
    {
        Tag root=null;
        try {
             root = HTMLParser.compile(textArea.getText());
            Page page =new Page(window , "","");
            Drawer drawer = new FXDrawer(this.window.getTab() , page ,window.getUi() , "non");
            root.draw(drawer);
            result= page.getContent();
        } catch (Exception e) {
            result = new ScrollPane(new Label(e+"" ));
        }
        finally {
            this.window.updateTabContent();

        }
    }



    public void setStyle(Style style)
    {
        this.style =style;
        switch (style)
        {
            case DEFAULT:
                textArea.setStyle("-fx-control-inner-background:#ffffff; -fx-font-family: Consolas; -fx-highlight-fill: #0000ff; -fx-highlight-text-fill: #ffff; -fx-text-fill: #000000");
                break;
            case BLACK:
                textArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00");
                break;
            case DARKGRAY:
                textArea.setStyle("-fx-control-inner-background:#484848; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #ffffff");
        }
    }

    public void setMod(MOD mod)
    {
        this.mod = mod;
    }





    boolean active= false;
    private void activeTimer()
    {
        active =true;
        Thread thread = new Thread(()->{
            while (active){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(this::updateViewSide);
            }
        });
        thread.start();

    }
    private void disActiveTimer()
    {
        active = false;
    }

    @Override
    public Node getContent() {
        HBox hBox = new HBox(editSide ,result);
        //hBox.setPadding(new Insets(10,10,10,10));
        return hBox;
    }
}
