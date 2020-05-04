package exception;

/**
 * QuitOnFinishException is thrown when the user decides to terminate the program and quit.
 */
public class QuitOnFinishException extends RuntimeException {
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
