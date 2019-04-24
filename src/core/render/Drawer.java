package core.render;


import javafx.scene.image.Image;

public interface Drawer {
    enum ATTRIBUTES{
        FONT_BOLD,
        FONT_ITALIC,
        FONT_UNDERLINE,
    }

    enum ALIGN
    {
        CENTER , LEFT , RIGHT
    }
    public void drawText(String text);
    public void setTitle(String text);
    public void drawImage(Image image);
    public void useAttribute(ATTRIBUTES attributes);
    public void unUseAttribute(ATTRIBUTES attributes);
    public Boolean hasAttribute(ATTRIBUTES attributes);
    public void drawTable();
    public void usePane(DrawerPane drawerPane);
    public void unUsePane();
    public void useAlign(ALIGN align);
    public void unUseAlign();



}
