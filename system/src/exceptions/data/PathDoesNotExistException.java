package exceptions.data;

public class PathDoesNotExistException extends Exception {
    private final String EXCEPTION_MESSAGE = "There is no such path.";

    public PathDoesNotExistException() {
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
