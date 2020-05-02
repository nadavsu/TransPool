package exceptions.data;

public class StopNameDuplicationException extends TransPoolDataException {
    private String message;

    public StopNameDuplicationException(String stopName) {
        message = "There are two or more stops named " + stopName + ".";
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
