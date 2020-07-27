package data.transpool.user.account;

import data.transpool.TransPoolMap;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;

import java.util.Collection;

public interface Rider {
    Collection<TripRequest> getAllTripRequests();
    Collection<MatchedTripRequest> getAllMatchedTripRequests();

    TripRequest getRequest(int ID);
    MatchedTripRequest getMatchedTripRequest(int ID);

    void addRequest(TransPoolMap map, TripRequest request);
    void acceptMatch(TransPoolMap map, MatchedTripRequest matchedRequest);

}
