package data.transpool.user.account;

import data.transpool.SingleMapEngine;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestDTO;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.trip.request.component.TripRequestDTO;

import java.util.Collection;

public interface Rider {
    Collection<TripRequest> getAllTripRequests();
    Collection<MatchedTripRequest> getAllMatchedTripRequests();

    Collection<TripRequestDTO> getTripRequestsDetails();
    Collection<MatchedTripRequestDTO> getMatchedTripRequestDetails();

    TripRequest getRequest(int ID);
    MatchedTripRequest getMatchedTripRequest(int ID);

    void addRequest(SingleMapEngine map, TripRequest request);
    void acceptMatch(SingleMapEngine map, MatchedTripRequest matchedRequest);

}
