import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {
    private Stage mainStage ;
    private Scene scene;
    private TabPane tabPane;
    private List<Pair<Window,Integer> >windows = new ArrayList<>();
    private Integer tabId=0;

    public void initializeUserInterface()
    {
        //Stage

        mainStage = new Stage();
        mainStage.setMaximized(true);
        mainStage.setTitle("Matté");
        mainStage.initStyle(StageStyle.DECORATED);
        Image image=new Image(getClass().getResourceAsStream("Img\\mate.png"));
        mainStage.getIcons().add(image);

        //TapPane
        tabPane = new TabPane();
        tabPane.setPrefWidth(1500);
        //Window
        windows.add(new Pair<>(new Window(),tabId));
        addNewTab();

    }
    private void addNewTab()
    {
        Tab tab = new Tab("NewTab");
        tab.setId(tabId++ +"");
        Image image =new Image(getClass().getResourceAsStream("Img\\home1.png"));
        ImageView imageView =new ImageView(image);
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        tab.setGraphic(imageView);
        tab.closableProperty();//what is this???
        tab.setContent(windows.get(windows.size()-1).getKey().getContent());
        setActions();
        tab.setOnClosed((event) ->{
            windows.remove(getIndexById(tabId));
        } );
        tabPane.getTabs().add(tab);
        //to select new tab
        SingleSelectionModel<Tab> singleSelectionModel = tabPane.getSelectionModel();
        singleSelectionModel.select(tab);
    }
    public void showUI()
    {
        initializeUserInterface();
        scene = new Scene(tabPane);

        mainStage.setScene(scene);
        mainStage.show();
    }
   private void setActions()
    {
        windows.get(windows.size()-1).getKey().getPageToolBar().getNewTabButton().setOnAction(
                (e)->{
                    windows.add(new Pair<>(new Window(),tabId));
                    addNewTab();
                }
        );
    }
    private int getIndexById(int id)
    {
        for (int i =0;i<windows.size();i++)
            if (windows.get(i).getValue()==id)
                return i;
        return -1;
    }
}
