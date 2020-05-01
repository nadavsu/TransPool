package exceptions.data;

//TODO: More informaion on the exception message - list the stops which are duplicated.
// You'll need to move this exception to the engine api in order to pass the list of duplicated stops.
public class StopDuplicationException extends TransPoolDataException {
    private final String EXCEPTION_MESSAGE = "There are two stops with the same name or coordinates.";

    public StopDuplicationException() {

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
