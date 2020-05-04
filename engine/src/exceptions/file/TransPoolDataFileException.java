package exceptions.file;

import java.io.IOException;

public class TransPoolDataFileException extends Exception {
    private final String EXCEPTION_MESSAGE = "Data in the file is invalid!";

    public TransPoolDataFileException() {

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
