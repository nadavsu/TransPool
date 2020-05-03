package exceptions;

public class NoMatchesFoundException extends Exception {
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
