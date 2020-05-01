package exceptions.data;

//TODO: Find the stop that is out of bounds and pass it as a parameter in the constructor.
public class StopOutOfBoundsException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "Stop coordinates are out of bounds.";

    public StopOutOfBoundsException() {

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
