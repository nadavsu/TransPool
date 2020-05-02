package data.transpool.structures;

import data.transpool.TransPoolTripRequest;
import data.transpool.TripRequest;

import java.util.ArrayList;
import java.util.List;

public class TransPoolTripRequests {
    private List<TripRequest> transpoolTripRequests;

    public TransPoolTripRequests() {
        transpoolTripRequests = new ArrayList<>();
    }

    public void addTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest) {
        transpoolTripRequests.add(transpoolTripRequest);
    }

    public List<TripRequest> getTranspoolTripRequests() {
        return transpoolTripRequests;
    }

    @Override
    public String toString() {
        StringBuilder allRequests = new StringBuilder();
        for (TripRequest request : transpoolTripRequests) {
            allRequests.append(request.toString());
            allRequests.append("\n");
        }
        return allRequests.toString();
    }
}
