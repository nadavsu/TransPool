package exception;

/**
 * Thrown when no matches were found for a ride request.
 */
public class NoMatchesFoundException extends TransPoolRunTimeException {
    private final String EXCEPTION_MESSAGE = "We couldn't find any matches for the your ride :(";

    public NoMatchesFoundException() {
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
