package api.transpool.trip.request;

import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.MatchedTripRequestDTO;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.trip.request.component.TripRequestDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds TripRequests and MatchedTripRequests, and functions that control them.
 * Like a TripRequestManager
 */

public class TripRequestsEngineBase implements TripRequestsEngine {

    private Map<Integer, TripRequest> allTripRequests;
    private Map<Integer, MatchedTripRequest> allMatchedTripRequests;

    public TripRequestsEngineBase() {
        this.allTripRequests = new HashMap<>();
        this.allMatchedTripRequests = new HashMap<>();
    }

    @Override
    public List<TripRequestDTO> getTripRequestsDetails() {
        return allTripRequests
                .values()
                .stream()
                .map(TripRequest::getDetails)
                .collect(Collectors.toList());
    }


    @Override
    public List<MatchedTripRequestDTO> getMatchedTripsDetails() {
        return allMatchedTripRequests
                .values()
                .stream()
                .map(MatchedTripRequest::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public TripRequest getTripRequest(int ID) {
        return allTripRequests.get(ID);
    }

    @Override
    public MatchedTripRequest getMatchedTripRequest(int ID) {
        return allMatchedTripRequests.get(ID);
    }

    @Override
    public void deleteTripRequest(TripRequest requestToDelete) {
        allTripRequests.remove(requestToDelete.getID());
    }

    @Override
    public void addTripRequest(TripRequest tripRequest) {
        allTripRequests.put(tripRequest.getID(), tripRequest);
    }

    @Override
    public int getNumOfTripRequests() {
        return allTripRequests.size() + allMatchedTripRequests.size();
    }

    @Override
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        allMatchedTripRequests.put(matchedTripRequest.getID(), matchedTripRequest);
        deleteTripRequest(getTripRequest(matchedTripRequest.getID()));
    }

    @Override
    public Map<Integer, TripRequest> getAllTripRequests() {
        return allTripRequests;
    }

    @Override
    public Map<Integer, MatchedTripRequest> getAllMatchedTripRequests() {
        return allMatchedTripRequests;
    }

    @Override
    public int getNumOfMatchedRequests() {
        return allMatchedTripRequests.size();
    }
}
