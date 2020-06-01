package api.task;

import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import javafx.concurrent.Task;

import java.util.List;

public class LoadUITask extends Task<Boolean> {

    List<TransPoolTrip> theRequests;

    public LoadUITask(List<TransPoolTrip> theRequests) {
        this.theRequests = theRequests;
    }

    @Override
    protected Boolean call() throws Exception {
        return null;
    }
}
