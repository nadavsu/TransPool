package api;

import api.transpool.SingleMapEngineDTO;
import api.transpool.SingleMapEngine;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.account.TransPoolRider;

import java.util.Collection;
import java.util.Map;


//test comment.

public interface MapsEngine {
    Map<String, SingleMapEngine> getMaps();

    void addMap(SingleMapEngine map);

    SingleMapEngine getMap(String mapName);

    boolean isMapExists(String mapName);

    Collection<SingleMapEngineDTO> getAllMapEnginesDetails();

    void addNewTripRequest(TripRequest request, TransPoolRider rider, String mapName);

    void addNewTripOffer(TripOffer offer, TransPoolDriver driver, String mapName);

    void addNewMatchedTripRequest(MatchedTripRequest matchedRequest, TransPoolRider rider, String mapName);
}
