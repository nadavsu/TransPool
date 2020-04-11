package api.exceptions;

public class QuitOnFinishException extends Exception {
    private final String EXCEPTION_MESSAGE = "Thank you for using TransPool!";

    public QuitOnFinishException() {
    }

    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }

    @Override
    public String toString() {
        return EXCEPTION_MESSAGE;
    }
}
