package exceptions.time;

public class InvalidTimeException extends Exception {
    private final String EXCEPTION_MESSAGE = "Time is not valid! Must be in the range of 00:00 to 23:59";

    public InvalidTimeException() {
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
