package exceptions.file;

public class StopCoordinatesDuplicationException extends TransPoolFileDataException {
    private String message;

    public StopCoordinatesDuplicationException(int x, int y) {
        message = "There are two or more stops with the coordinates (" + x + "," + y + ").";
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
