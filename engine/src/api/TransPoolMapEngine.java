package api;

import data.transpool.TransPoolMap;

import java.util.HashMap;
import java.util.Map;

public class TransPoolMapEngine {

    //Name to map map.
    private Map<String, TransPoolMap> maps;

    public TransPoolMapEngine() {
        maps = new HashMap<>();
    }

    public synchronized void addMap(TransPoolMap map) {
        maps.put(map.getMapName(), map);
    }

    public TransPoolMap getMap(String mapName) {
        return maps.get(mapName);
    }

    public boolean isMapExists(String mapName) {
        return maps.containsKey(mapName);
    }

}
