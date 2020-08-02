package api;

import data.transpool.SingleMapEngineDTO;
import data.transpool.SingleMapEngine;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.user.account.TransPoolDriver;
import data.transpool.user.account.TransPoolRider;

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
    public Map<String, SingleMapEngine> getMaps() {
        return Collections.unmodifiableMap(maps);
    }

    @Override
    public List<SingleMapEngineDTO> getMapDetailsDTOs() {
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
