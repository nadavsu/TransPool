package api;

import data.transpool.MapDetailsDTO;
import data.transpool.TransPoolMap;

import java.util.*;


/**
 * Holds and manages all maps in the system.
 */
public class MapEngineBase implements MapEngine {

    //Name to map map.
    private Map<String, TransPoolMap> maps;

    public MapEngineBase() {
        maps = new HashMap<>();
    }

    @Override
    public synchronized void addMap(TransPoolMap map) {
        maps.put(map.getMapName(), map);
    }

    @Override
    public TransPoolMap getMap(String mapName) {
        return maps.get(mapName);
    }

    @Override
    public boolean isMapExists(String mapName) {
        return maps.containsKey(mapName);
    }

    @Override
    public Map<String, TransPoolMap> getMaps() {
        return Collections.unmodifiableMap(maps);
    }

    @Override
    public List<MapDetailsDTO> getMapDetailsDTOs() {
        List<MapDetailsDTO> mapDetailsDTOs = new ArrayList<>();
        maps.forEach((mapName, map) -> mapDetailsDTOs.add(map.getDetails()));
        return mapDetailsDTOs;
    }
}
