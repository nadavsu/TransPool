package exceptions.file;

public class MapDimensionsException extends TransPoolDataFileException {
    private final String EXCEPTION_MESSAGE = "Map dimensions are invalid.";

    public MapDimensionsException() {

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
