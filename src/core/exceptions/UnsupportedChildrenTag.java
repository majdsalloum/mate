package core.exceptions;

public class UnsupportedChildrenTag extends Exception {
    public UnsupportedChildrenTag(String childrenType,String tag) {
        super(childrenType + "Isn't supported for " + tag);
    }
}
