package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;
import core.render.DrawerPane;
import javafx.scene.layout.GridPane;

public class TABLE extends Tag {
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES, new String[]{"border"});
    protected final static String[] CHILDREN_TYPES = {"thead","tbody","tr","tfoot","colgroup"};

    void validate() throws InvalidContentException {
        for(int i=0;i<children.size();i++)
        if ( children.get(i).getClass() == TR.class)
            return;
        throw new InvalidContentException("TABLE TAG MUST HAVE AT LEAST ONE CHILDREN (TR)");
    }
    @Override
    public void draw(Drawer drawer) {
        for (Object item : children)
        {
            if (!(item instanceof Tag))
                drawer.drawText((String) item);
        }
        for (Object item:children)
        {
            if (item.toString()=="CAPTION") {
                DrawerPane drawerPane = new DrawerPane();
                drawerPane.setDrawing_parent(DrawerPane.DRAWING_PARENT.VBOX);
                drawer.usePane(drawerPane);
                drawer.useAlign(Drawer.ALIGN.CENTER);
                for (Object item2 : ((Tag) item).children) {
                    if (!(item2 instanceof Tag)) {
                        drawer.drawText((String) item2);
                    } else ((Tag) item2).draw(drawer);
                }
                drawer.unUseAlign();
            }
        }
        DrawerPane drawerPane  =new DrawerPane();
        drawerPane.setDrawing_parent(DrawerPane.DRAWING_PARENT.TABLE);
        drawerPane.setParent(new GridPane());
        drawer.usePane(drawerPane);
        for (Object item:children)
        {
            if (item.toString()=="TR")
            {
                drawerPane.setRow(drawerPane.getRow()+1);
                ((Tag)item).draw(drawer);
            }
            else if (item.toString()=="TD") {
                drawerPane.setCol(drawerPane.getCol() + 1);
                ((Tag)item).draw(drawer);
            }
            else if (item.toString()=="TH")
            {
                drawer.useAttribute(Drawer.ATTRIBUTES.FONT_BOLD);
                drawer.useAlign(Drawer.ALIGN.CENTER);

                drawerPane.setCol(drawerPane.getCol() + 1);
                ((Tag)item).draw(drawer);

                drawer.unUseAttribute(Drawer.ATTRIBUTES.FONT_BOLD);
                drawer.unUseAlign();
            }
        }
        drawer.unUsePane();
    }
}
