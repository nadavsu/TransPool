package exceptions.data;

public class StopNotFoundException extends Exception {
    private final String EXCEPTION_MESSAGE = "Stop does not exist!";

    public StopNotFoundException() {
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
