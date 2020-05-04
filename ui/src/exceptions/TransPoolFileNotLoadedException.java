package exceptions;

/**
 * Thrown when a TransPool data file is not loaded and the user is trying to choose an option in the menu that requires
 * a TransPool file.
 */
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
