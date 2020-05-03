package exceptions;

public class InvalidOptionException extends Exception {
    private final String EXCEPTION_MESSAGE = "The option you have chosen is not valid.";

    public InvalidOptionException() {
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
