package api.components;

import api.controller.TransPoolTripRequestController;
import data.transpool.TransPoolData;
import data.transpool.map.Map;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.Time;
import data.transpool.trip.TransPoolTripRequest;
import exception.file.StopNotFoundException;
import exception.time.InvalidTimeException;

import java.time.LocalTime;
import java.util.List;
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
    public TransPoolTripRequest createNewTransPoolTripRequest(String riderName, String source, String destination,
                                                              LocalTime time, boolean isArrivalTime, boolean isContinuous)
                                                              throws StopNotFoundException {
        if (!Map.containsStop(source)) {
            throw new StopNotFoundException(source);
        }
        if (!Map.containsStop(destination)) {
            throw new StopNotFoundException(destination);
        }

        return new TransPoolTripRequest(riderName, source, destination, time, isArrivalTime, isContinuous);
    }

    public List<String> getAllTransPoolTripRequestsAsStrings(TransPoolData data) {
        List<String> matchedAndUnmatchedTransPoolTripRequests = data
                .getAllTransPoolTripRequests()
                .stream()
                .map(TransPoolTripRequest::toString)
                .collect(Collectors.toList());

        matchedAndUnmatchedTransPoolTripRequests
                .addAll(data
                        .getAllMatchedTrips()
                        .stream()
                        .map(MatchedTransPoolTripRequest::toString)
                        .collect(Collectors.toList()));

        return matchedAndUnmatchedTransPoolTripRequests;
    }

    public List<TransPoolTripRequest> getAllTransPoolTripRequests(TransPoolData data) {
        return data.getAllTransPoolTripRequests();
    }
}
