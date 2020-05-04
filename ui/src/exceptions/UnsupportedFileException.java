package exceptions;

import java.io.IOException;

/**
 * Thrown when the user tries to load any other file-type other than .XML.
 */
public class UnsupportedFileException extends IOException {
    private final String EXCEPTION_MESSAGE = "File type is not supported! File type must be of type XML.";

    public UnsupportedFileException() {

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
