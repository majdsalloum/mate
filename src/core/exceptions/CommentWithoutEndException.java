package core.exceptions;

public class CommentWithoutEndException extends InvalidSyntaxException {

    public CommentWithoutEndException(String message) {
        super(message);
    }
}
