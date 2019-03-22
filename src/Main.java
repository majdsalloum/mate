import GUI.UserInterface;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application

{
    UserInterface ui=new UserInterface();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       ui. showUI();
    }
    }