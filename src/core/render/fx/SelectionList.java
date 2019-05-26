package core.render.fx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class SelectionList {
    VBox list;
    Boolean multiple =false;
    Integer size;
    final int LABEL_HEIGHT = 20;
    public SelectionList(Boolean multiple)
    {
        this.multiple=multiple;
        list = new VBox();
        list.setAlignment(Pos.CENTER);
        list.setBackground(new Background(new BackgroundFill(Color.WHITE ,null,null)));
        list.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                list.setPrefWidth(Math.max((Double) newValue, (Double) oldValue));
            }
        });
    }
    public void addItem(Button button)
    {
        button.setAlignment(Pos.CENTER);
        button.setGraphic(new Label());
        list.getChildren().add(button);
        button.setPrefHeight(LABEL_HEIGHT);
        disSelect(button);
        button.setOnAction((e)->{
            if(!button.isDisable()){
                if(isSelected(button))
                    disSelect(button);
                else
                    select(button);}

        });
    }
    public void select(Button label)
    {
        if (!multiple) {
            while (!getSelected().isEmpty())
                disSelect( getSelected().getFirst());
        }
        label.setTextFill(Color.WHITE);
        label.setBackground(new Background(new BackgroundFill(Color.BLUE ,null,null)));
    }
    public void disSelect(Button label)
    {
        label.setTextFill(Color.BLACK);
        label.setBackground(new Background(new BackgroundFill(Color.WHITE ,null,null)));
    }
    public Boolean isSelected(Button label)
    {
        return label.getTextFill().equals(Color.WHITE);
    }
    public LinkedList<Button> getSelected()
    {
        LinkedList<Button> labels = new LinkedList<>();
        for(Node label : list.getChildren())
        {
            if (isSelected((Button) label))
                labels.add((Button) label);
        }
        return labels;
    }
    public Node getContent()
    {
        ScrollPane scrollPane  = new ScrollPane(list);
        if(size==null)
            scrollPane.setPrefHeight(list.getChildren().size()*LABEL_HEIGHT+10);
        else scrollPane.setPrefHeight(size*LABEL_HEIGHT+10);
        return scrollPane;
    }
    public void addGroup(String s)
    {
        Label label = new Label(s);
        label.setAlignment(Pos.CENTER_LEFT);
        list.getChildren().add(label);
    }
    public void setSize(Integer integer)
    {
        this.size=integer;
    }
}
