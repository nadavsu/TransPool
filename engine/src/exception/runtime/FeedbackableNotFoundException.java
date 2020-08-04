package exception.runtime;

public class FeedbackableNotFoundException extends TransPoolRunTimeException {
    private final String EXCEPTION_MESSAGE = "You haven't taken a ride with this user yet.";

    public FeedbackableNotFoundException() {

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
