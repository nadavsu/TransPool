package exceptions.file;

public class StopOutOfBoundsException extends TransPoolDataFileException {
    private String message;

    public StopOutOfBoundsException(String stopName) {
        this.message = "The stop " + stopName + " is out of the map bounds.";
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
