package exceptions.file;

public class PathDoesNotExistException extends TransPoolDataFileException {
    private String message;

    public PathDoesNotExistException(String source, String destination) {
        message = "There is no such path from " + source + " to " + destination + ".";
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
