package gui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class UserInterface {
    private Stage mainStage;
    private Scene scene;
    private TabPane tabPane;
    private List<Window> windows;


    public void initializeUserInterface() {
        //Stage
        mainStage = new Stage();
        mainStage.setMaximized(true);
        mainStage.setTitle("Matté");
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.getIcons().add(Images.logo);

        //TapPane
        tabPane = new TabPane();
        tabPane.setPrefWidth(1500);
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        //List Window
        windows = new ArrayList<>();
        createNewWindow();

    }

    public void showUI() {
        initializeUserInterface();
        scene = new Scene(tabPane);

        mainStage.setScene(scene);
        mainStage.show();
    }

    private void setTabCloseAction(Window window) {
        window.getTab().setOnClosed((e) ->
        {
            if (windows.size() > 1)
                windows.remove(window);
            else
                mainStage.close();
        });
    }

    private void setAddTabAction(Window window) {
        Button button = window.getPageToolBar().getNewTabButton();
        button.setOnAction((e) -> {
            createNewWindow();
        });
        MenuItem menuItem = window.getPageToolBar().getNewTab();
        menuItem.setOnAction((e) -> {
            createNewWindow();
        });
    }

    public Window createNewWindow() {
        Window newWindow = new Window(tabPane, this);
        windows.add(newWindow);
        //setActions
        setTabCloseAction(newWindow);
        setAddTabAction(newWindow);

        //Select the new tab to show it
        SingleSelectionModel singleSelectionModel = tabPane.getSelectionModel();
        singleSelectionModel.select(newWindow.getTab());
        return newWindow;
    }

}
