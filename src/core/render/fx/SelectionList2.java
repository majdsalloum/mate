package core.render.fx;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;


public class SelectionList2 {
    ChoiceBox list;
    Boolean multiple ;
    Integer size;
    public SelectionList2(Boolean multiple)
    {
        this.multiple=multiple;
        list=new ChoiceBox();
    }
    public void addItem(String text)
    {
        list.getItems().add(text);
    }
    public void select(String text)
    {
        list.getSelectionModel().select(text);
    }
    public void disSelect(Button label)
    {
        label.setTextFill(Color.BLACK);
        label.setBackground(new Background(new BackgroundFill(Color.WHITE ,null,null)));
    }
    public Boolean isSelected(String text)
    {
        return list.getSelectionModel().getSelectedItem().equals(text);
    }

    public Node getContent()
    {
        return list;
    }
    public void addGroup(String s)
    {
    }
    public void setSize(Integer integer)
    {
        this.size=integer;
    }
}
