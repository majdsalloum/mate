package core.render;


import javafx.scene.image.Image;

public interface Drawer {
    public void drawText(String text);
    public void setTitle(String text);
    public void drawImage(Image image);
}
