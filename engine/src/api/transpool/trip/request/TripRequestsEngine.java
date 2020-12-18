package api.transpool.trip.request;

import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.MatchedTripRequestDTO;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.trip.request.component.TripRequestDTO;

import java.util.List;
import java.util.Map;

/**
 * THe engine interface that controls the trip requests.
 */
public interface TripRequestsEngine {
    List<TripRequestDTO> getTripRequestsDetails();

    List<MatchedTripRequestDTO> getMatchedTripsDetails();

    TripRequest getTripRequest(int ID);

    void deleteTripRequest(TripRequest requestToDelete);

    void addTripRequest(TripRequest tripRequest);

    void addMatchedRequest(MatchedTripRequest matchedTripRequest);

    Map<Integer, TripRequest> getAllTripRequests();

    Map<Integer, MatchedTripRequest> getAllMatchedTripRequests();

    MatchedTripRequest getMatchedTripRequest(int feedbackerID);

    int getNumOfTripRequests();

    int getNumOfMatchedRequests();
}
