package core.exceptions;

public class UnsupportedAttributeException extends InvalidContentException{
    public UnsupportedAttributeException(String key,String Tag) {
        super(key + " Not Supported For Tag ");
    }
}
