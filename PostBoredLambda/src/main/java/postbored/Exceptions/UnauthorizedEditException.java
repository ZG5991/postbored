package postbored.Exceptions;


public class UnauthorizedEditException extends Exception {

    private static final long serialVersionUID = 5305207012265223597L;

    public UnauthorizedEditException() {}

    public UnauthorizedEditException(String message) {
        super(message);
    }

    public UnauthorizedEditException(Throwable cause) {
        super (cause);
    }

    public UnauthorizedEditException(String message, Throwable cause) {
        super (message, cause);
    }

}
