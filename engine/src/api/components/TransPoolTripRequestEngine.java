package api.components;

import data.transpool.TransPoolData;
import data.transpool.map.Map;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.TransPoolTripRequest;
import exception.file.StopNotFoundException;
import exception.time.InvalidTimeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.stream.Collectors;

public class TransPoolTripRequestEngine {

    /**
     * Creates a new trip request using the given parameters.
     * @param riderName - The name of the requester.
     * @param source - Name of stop the requester gets on.
     * @param destination - Name of stop the requester gets off.
     * @param time - LocalTime of request.
     * @param isArrivalTime - true for time of arrival, false for time of departure.
     * @param isContinuous - true if the requester would like to get on a single continuous ride.
     * @throws InvalidTimeException - If time is not valid.
     * @throws StopNotFoundException - If the desired destination or source do not exist.
     */
    public TransPoolTripRequest createNewTransPoolTripRequest(Map map, String riderName, String source, String destination,
                                                              LocalTime time, boolean isArrivalTime, boolean isContinuous)
                                                              throws StopNotFoundException {
        return new TransPoolTripRequest(map, riderName, source, destination, time, isArrivalTime, isContinuous);
    }

    public ObservableList<String> getAllTransPoolTripRequestsAsStrings(TransPoolData data) {
        ObservableList<String> matchedAndUnmatchedTransPoolTripRequests = data
                .getAllTransPoolTripRequests()
                .stream()
                .map(TransPoolTripRequest::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        matchedAndUnmatchedTransPoolTripRequests
                .addAll(data
                        .getAllMatchedTrips()
                        .stream()
                        .map(MatchedTransPoolTripRequest::toString)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        return matchedAndUnmatchedTransPoolTripRequests;
    }

    public ObservableList<TransPoolTripRequest> getAllTransPoolTripRequests(TransPoolData data) {
        return data.getAllTransPoolTripRequests();
    }
}
