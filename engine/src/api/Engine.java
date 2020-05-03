package api;

import data.transpool.TransPoolData;
import data.transpool.structures.TransPoolTripRequests;
import data.transpool.structures.TransPoolTrips;
import data.transpool.trips.MatchedTransPoolTripRequest;
import data.transpool.trips.TransPoolTripRequest;
import data.transpool.user.TransPoolDriver;

import java.util.List;
import java.util.stream.Collectors;

public class Engine {

    protected static TransPoolData data;

    public Engine() { }

    public TransPoolData getData() {
        return data;
    }

    public TransPoolTrips getAllTransPoolTrips() {
        return TransPoolData.getAllTransPoolTrips();
    }

    public TransPoolTripRequests getAllTransPoolTripRequests() {
        return TransPoolData.getAllTransPoolTripRequests();
    }

    public List<String> getAllTransPoolTripRequestsAsStrings() {
        List<String> unmatchedTransPoolTripRequestStrings = TransPoolData
                .getAllTransPoolTripRequests()
                .getTranspoolTripRequests()
                .stream()
                .filter(t -> !t.isMatched())
                .map(TransPoolTripRequest::toString)
                .collect(Collectors.toList());

        unmatchedTransPoolTripRequestStrings
                .addAll(TransPoolData
                .getAllMatchedTrips()
                .stream()
                .map(MatchedTransPoolTripRequest::toString)
                .collect(Collectors.toList()));

        return unmatchedTransPoolTripRequestStrings;
    }

    public boolean isFileLoaded() {
        return TransPoolData.isLoaded;
    }

}
