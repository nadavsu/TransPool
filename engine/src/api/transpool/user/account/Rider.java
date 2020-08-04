package api.transpool.user.account;

import api.transpool.SingleMapEngine;
import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.MatchedTripRequestDTO;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.trip.request.component.TripRequestDTO;

import java.util.Collection;

public interface Rider {
    Collection<TripRequest> getAllTripRequests();
    Collection<MatchedTripRequest> getAllMatchedTripRequests();

    Collection<TripRequestDTO> getTripRequestsDetails();
    Collection<TripRequestDTO> getTripRequestsDetailsFromMap(SingleMapEngine map);
    Collection<MatchedTripRequestDTO> getMatchedTripRequestDetails();

    TripRequest getRequest(int ID);
    MatchedTripRequest getMatchedTripRequest(int ID);

    void addRequest(TripRequest request);
    void acceptMatch(MatchedTripRequest matchedRequest);
}
