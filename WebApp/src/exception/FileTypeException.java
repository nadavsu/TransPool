package exception;

public class FileTypeException extends Exception {
    private final String EXCEPTION_MESSAGE = "File must be of type XML.";

    public FileTypeException() {
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
