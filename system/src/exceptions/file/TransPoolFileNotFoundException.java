package exceptions.file;

import java.io.FileNotFoundException;

public class TransPoolFileNotFoundException extends FileNotFoundException {
    private final String EXCEPTION_MESSAGE = "File not found!";

    public TransPoolFileNotFoundException() {

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
