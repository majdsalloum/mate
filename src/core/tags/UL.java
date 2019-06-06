package core.tags;

import core.exceptions.InvalidContentException;
import core.render.Drawer;

public class UL extends Tag {
    protected final static String[] CHILDREN_TYPES = {"li"};
    protected final static String[] SUPPORTED_ATTRIBUTES = CommonAttributes.joinArrays(CommonAttributes.GLOBAL_HTML_ATTRIBUTES,new String[]{"type"});

   void validate() {
            return;
    }
    @Override
    public void actualDraw(Drawer drawer) {
       drawer.drawUnOrderedList("disk");
       for (Object  item : children)
       {
           if (!(item instanceof Tag))
               drawer.drawText((String) item);
           else
           ((Tag)item).draw(drawer);

       }
       drawer.endDrawUnOrderedList();
    }
}
