package exceptions.data;

public class PathDoesNotExistException extends TransPoolDataException{
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
