package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class OL extends Tag {
    protected final static String[] CHILDREN_TYPES = {"LI"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"start", "reversed","type"});
    String start ="1";
    String type="1";
    void validate() throws InvalidContentException {
        for(int i=0;i<children.size();i++)
        if ( children.get(i).getClass() == LI.class)
            return;
        throw new InvalidContentException("OL TAG MUST HAVE AT LEAST ONE CHILDREN (LI )");
    }

    @Override
    public void actualDraw(Drawer drawer) {
        drawer.drawOrderedList(start,type);
        for (Object  item : children)
        {
            if (!(item instanceof Tag))
                drawer.drawText((String) item);
            else
                ((Tag)item).draw(drawer);

        }
        drawer.endDrawOrderedList();
    }

}
