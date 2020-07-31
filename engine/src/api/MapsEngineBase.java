package api;

import data.transpool.MapDetailsDTO;
import data.transpool.SingleMapEngine;

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
    public List<MapDetailsDTO> getMapDetailsDTOs() {
        List<MapDetailsDTO> mapDetailsDTOs = new ArrayList<>();
        maps.forEach((mapName, map) -> mapDetailsDTOs.add(map.getMapDetails()));
        return mapDetailsDTOs;
    }
}
