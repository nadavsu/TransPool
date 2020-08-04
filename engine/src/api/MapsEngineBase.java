package api;

import api.transpool.SingleMapEngineDTO;
import api.transpool.SingleMapEngine;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.account.TransPoolRider;

import java.util.*;


/**
 * Holds and manages all maps in the system.
 */
public class MapsEngineBase implements MapsEngine {

    //Name to map map.
    private Map<String, SingleMapEngine> maps;

    public MapsEngineBase() {
        maps = new HashMap<>();
    }

    @Override
    public synchronized void addMap(SingleMapEngine map) {
        maps.put(map.getMapName(), map);
    }

    @Override
    public SingleMapEngine getMap(String mapName) {
        return maps.get(mapName);
    }

    @Override
    public boolean isMapExists(String mapName) {
        return maps.containsKey(mapName);
    }

    @Override
    public synchronized Map<String, SingleMapEngine> getMaps() {
        return Collections.unmodifiableMap(maps);
    }

    @Override
    public List<SingleMapEngineDTO> getAllMapEnginesDetails() {
        List<SingleMapEngineDTO> singleMapEngineDTOS = new ArrayList<>();
        maps.forEach((mapName, map) -> singleMapEngineDTOS.add(map.getMapEngineDetails()));
        return singleMapEngineDTOS;
    }

    @Override
    public void addNewTripRequest(TripRequest request, TransPoolRider rider, String mapName) {
        rider.addRequest(request);
        maps.get(mapName).addTripRequest(request);
    }

    @Override
    public void addNewTripOffer(TripOffer offer, TransPoolDriver driver, String mapName) {
        driver.addTripOffer(offer);
        maps.get(mapName).addTripOffer(offer);
    }

    @Override
    public void addNewMatchedTripRequest(MatchedTripRequest matchedRequest, TransPoolRider rider, String mapName) {
        rider.acceptMatch(matchedRequest);
        maps.get(mapName).addMatchedRequest(matchedRequest);
    }
}
