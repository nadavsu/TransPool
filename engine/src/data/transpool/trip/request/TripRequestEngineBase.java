package data.transpool.trip.request;

import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestDTO;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.trip.request.component.TripRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TripRequestEngineBase implements TripRequestEngine {

    private List<TripRequest> allTripRequests;
    private List<MatchedTripRequest> allMatchedTripRequests;

    public TripRequestEngineBase() {
        this.allTripRequests = new ArrayList<>();
        this.allMatchedTripRequests = new ArrayList<>();
    }

    @Override
    public List<TripRequestDTO> getTripRequestsDetails() {
        return allTripRequests
                .stream()
                .map(TripRequest::getDetails)
                .collect(Collectors.toList());
    }


    @Override
    public List<MatchedTripRequestDTO> getMatchedTripsDetails() {
        return allMatchedTripRequests
                .stream()
                .map(MatchedTripRequest::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public TripRequest getTripRequest(int ID) {
        return allTripRequests
                .stream()
                .filter(t -> t.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public MatchedTripRequest getMatchedTripRequest(int ID) {
        return allMatchedTripRequests
                .stream()
                .filter(m -> m.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteTripRequest(TripRequest requestToDelete) {
        allTripRequests.remove(requestToDelete);
    }

    @Override
    public void addTripRequest(TripRequest tripRequest) {
        allTripRequests.add(tripRequest);
    }

    @Override
    public int getNumOfTripRequests() {
        return allTripRequests.size() + allMatchedTripRequests.size();
    }

    @Override
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        allMatchedTripRequests.add(matchedTripRequest);
        deleteTripRequest(getTripRequest(matchedTripRequest.getRequestID()));
    }

    @Override
    public List<TripRequest> getAllTripRequests() {
        return allTripRequests;
    }

    @Override
    public List<MatchedTripRequest> getAllMatchedTripRequests() {
        return allMatchedTripRequests;
    }

    @Override
    public int getNumOfMatchedRequests() {
        return allMatchedTripRequests.size();
    }
}
