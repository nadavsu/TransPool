package exceptions.data;

public class InvalidRouteException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "There is no such route.";

    public InvalidRouteException() {

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
