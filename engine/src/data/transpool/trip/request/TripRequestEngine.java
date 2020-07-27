package data.transpool.trip.request;

import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.List;

/**
 * THe engine interface that controls the trip requests.
 */
public interface TripRequestEngine {
    TripRequest getTripRequest(int ID);

    void deleteTripRequest(TripRequest requestToDelete);

    void addTripRequest(TripRequest tripRequest);

    void addMatchedRequest(MatchedTripRequest matchedTripRequest);

    List<TripRequest> getAllTripRequests();

    List<MatchedTripRequest> getAllMatchedTripRequests();

    MatchedTripRequest getMatchedTripRequest(int feedbackerID);

    int getNumOfTripRequests();

    int getNumOfMatchedRequests();
}
