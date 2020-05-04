package exception;

public class NoOptionsToShowException extends TransPoolRunTimeException {
    private final String EXCEPTION_MESSAGE = "There are no options to show.";

    public NoOptionsToShowException() {
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
