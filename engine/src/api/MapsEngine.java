package api;

import data.transpool.SingleMapEngineDTO;
import data.transpool.SingleMapEngine;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.user.account.TransPoolDriver;
import data.transpool.user.account.TransPoolRider;

import java.util.Collection;
import java.util.Map;

public interface MapsEngine {
    void addMap(SingleMapEngine map);

    SingleMapEngine getMap(String mapName);

    boolean isMapExists(String mapName);

    Map<String, SingleMapEngine> getMaps();

    Collection<SingleMapEngineDTO> getMapDetailsDTOs();

    void addNewTripRequest(TripRequest request, TransPoolRider rider, String mapName);

    void addNewTripOffer(TripOffer offer, TransPoolDriver driver, String mapName);

    void addNewMatchedTripRequest(MatchedTripRequest matchedRequest, TransPoolRider rider, String mapName);
}
