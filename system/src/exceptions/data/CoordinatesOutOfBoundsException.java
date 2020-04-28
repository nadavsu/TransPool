package exceptions.data;

public class CoordinatesOutOfBoundsException extends StopOutOfBoundsException {
    private final String EXCEPTION_MESSAGE = "The coordinates you are trying to create are out of bounds.";

    public CoordinatesOutOfBoundsException(){

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
