package exceptions.data;

import java.io.IOException;

public class StopNotFoundException extends IOException {
    private String stopName;
    private String message;

    public StopNotFoundException(String stopName) {
        this.stopName = stopName;
        this.message = "There is no such stop as " + stopName + ".";
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
