package exceptions.data;

public class PathDoesNotExistException extends TransPoolDataException {
    private String exceptionMessage;
    private String source;
    private String destination;

    public PathDoesNotExistException(String source, String destination) {
        this.source = source;
        this.destination = destination;
        exceptionMessage = "There is no such path from " + source + " to " + destination + ".";
    }

    @Override
    public String getMessage() {
        return exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
