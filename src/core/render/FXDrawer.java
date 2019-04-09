package core.render;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import gui.Page;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class FXDrawer implements Drawer {
    Tab tab;
    Page page;

    public FXDrawer(Tab tab, Page page) {
        this.tab = tab;
        this.page = page;
    }

    @Override
    public void drawText(String text) {
        Label label = new Label(text);
        label.setText(text);
        page.getFlowPane().getChildren().add(label);
    }

    @Override
    public void setTitle(String text) {
        tab.setText(text);
    }

    @Override
    public void drawImage(Image image)
    {
        Label label = new Label() ;
        ImageView imageView=new ImageView(image);
        label.setGraphic(imageView);
        page.getFlowPane().getChildren().add(label);
    }
}