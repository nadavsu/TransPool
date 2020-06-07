package api.components;

import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface TripRequestEngine {
    TripRequest getTripRequest(int ID);

    void deleteTripRequest(TripRequest requestToDelete);

    void addTripRequest(TripRequest tripRequest);

    void addMatchedRequest(MatchedTripRequest matchedTripRequest);

    ObservableList<TripRequest> getAllTripRequests();

    ObservableList<MatchedTripRequest> getAllMatchedTripRequests();
}
