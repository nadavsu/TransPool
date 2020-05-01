package exceptions.data;

import exceptions.data.TransPoolDataException;

public class PathDuplicationException extends TransPoolDataException {
    private String message;

    public PathDuplicationException(String source, String destination) {
        message = "Error with file.\nThere are two or more paths from " + source
                + " to " + destination + ".";
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
