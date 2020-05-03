package exceptions;

public class TripRequestAlreadyMatchedException extends Exception {
    private final String EXCEPTION_MESSAGE = "Trip request is already matched!";

    public TripRequestAlreadyMatchedException() {
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
