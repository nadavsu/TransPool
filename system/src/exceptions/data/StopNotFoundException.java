package exceptions.data;

public class StopNotFoundException extends RuntimeException {
    private String message;

    public StopNotFoundException(String stopName) {
        message = "There is no such stop as " + stopName + ".";
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
