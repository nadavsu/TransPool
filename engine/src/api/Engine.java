package api;

import api.controller.TransPoolController;
import data.transpool.TransPoolData;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The main engine of the program.
 * Handles the functions that deal with TransPoolData.
 */
public class Engine {

    protected static TransPoolData data;
    private TransPoolController controller;

    public Engine() {
    }

    public TransPoolData getData() {
        return data;
    }

    public List<TransPoolTrip> getAllTransPoolTrips() {
        return data.getAllTransPoolTrips();
    }

    public List<TransPoolTripRequest> getAllTransPoolTripRequests() {
        return data.getAllTransPoolTripRequests();
    }


    /**
     * Gets all trip requests (matched and unmatched) as a list of strings.
     * Gets the unmatched trip requests as a list of strings, and appends to that list the list of matched trip
     * requests as strings.
     * @return a list of strings of each all trip requests (matched & unmatched).
     * Todo: Make a super abstract class for matched and unmatched requests and use polymorphism. Then you can remove
     *       this function and use dynamic casting to print each trip.
     */
    public List<String> getAllTransPoolTripRequestsAsStrings() {
        List<String> matchedAndUmatchedTransPoolTripRequests = data
                .getAllTransPoolTripRequests()
                .stream()
                .map(TransPoolTripRequest::toString)
                .collect(Collectors.toList());

        matchedAndUmatchedTransPoolTripRequests
                .addAll(data
                .getAllMatchedTrips()
                .stream()
                .map(MatchedTransPoolTripRequest::toString)
                .collect(Collectors.toList()));

        return matchedAndUmatchedTransPoolTripRequests;
    }

    /**
     * Checks if a file is loaded to the system.
     * @return true if loaded, false otherwise.
     */
    public boolean isFileLoaded() {
        return TransPoolData.isLoaded;
    }

}
