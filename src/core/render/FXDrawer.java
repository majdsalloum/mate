package core.render;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import gui.Page;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;


public class FXDrawer implements Drawer {
    Tab tab;
    Page page;
    Integer font_bold=0;
    Integer font_italic=0;
    Integer font_underline=0;
    LinkedList<DrawerPane> parents=new LinkedList<>();
    LinkedList<ALIGN> aligns=new LinkedList<>();
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
    @Override
    public Boolean hasAttribute(ATTRIBUTES attributes){
        switch (attributes)
        {
            case FONT_BOLD:
                return font_bold>0;
            case FONT_ITALIC:
                return font_italic>0;
            case FONT_UNDERLINE:
                return font_underline>0;
                default:return false;
        }
    }
    @Override
    public void useAttribute(ATTRIBUTES attributes)
    {
        switch (attributes)
        {
            case FONT_BOLD:
                font_bold++;
                break;
            case FONT_ITALIC:
                font_italic++;
                break;
            case FONT_UNDERLINE:
                font_underline++;
                break;
        }
    }
    @Override
    public void unUseAttribute(ATTRIBUTES attributes)
    {
        switch (attributes)
        {
            case FONT_BOLD:
                font_bold--;
                break;
            case FONT_ITALIC:
                font_italic--;
                break;
            case FONT_UNDERLINE:
                font_underline--;
                break;
        }
    }
    @Override
    public void drawTable()
    {

    }

    @Override
    public void usePane(DrawerPane drawerPane) {

    }

    @Override
    public void unUsePane() {
        DrawerPane drawerPane = parents.pop();
        if (parents.isEmpty())
            page.getFlowPane().getChildren().add(drawerPane.parent);
        else
        {
            if (parents.peek().getDrawing_parent()==DrawerPane.DRAWING_PARENT.TABLE)
            {
                ((GridPane)parents.peek().getParent()).add(drawerPane.parent,drawerPane.col,drawerPane.row);
            }
        }
    }

    @Override
    public void useAlign(ALIGN align) {
        aligns.add(align);
    }

    @Override
    public void unUseAlign() {
       aligns.pop();
    }
}