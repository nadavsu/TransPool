package exceptions.data;

public class TransPoolTripRequestNotFoundException extends Exception {
    private final String EXCEPTION_MESSAGE = "The TransPool trip request you have searched for was not found.";

    public TransPoolTripRequestNotFoundException() {

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
