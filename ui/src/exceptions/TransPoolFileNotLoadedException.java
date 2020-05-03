package exceptions;

public class TransPoolFileNotLoadedException extends Exception {
    private final String EXCEPTION_MESSAGE = "No TransPool file loaded.";

    public TransPoolFileNotLoadedException() {

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
