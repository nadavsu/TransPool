package data.transpool.trip.request;

import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TripRequestEngineBase implements TripRequestEngine {

    private ObservableList<TripRequest> allTripRequests;
    private ObservableList<MatchedTripRequest> allMatchedTripRequests;

    public TripRequestEngineBase() {
        this.allTripRequests = FXCollections.observableArrayList();
        this.allMatchedTripRequests = FXCollections.observableArrayList();
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
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        allMatchedTripRequests.add(matchedTripRequest);
        deleteTripRequest(getTripRequest(matchedTripRequest.getRequestID()));
    }

    @Override
    public ObservableList<TripRequest> getAllTripRequests() {
        return allTripRequests;
    }

    @Override
    public ObservableList<MatchedTripRequest> getAllMatchedTripRequests() {
        return allMatchedTripRequests;
    }
}
