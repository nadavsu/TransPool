package api;

import data.transpool.MapDetailsDTO;
import data.transpool.TransPoolMap;

import java.util.Collection;
import java.util.Map;

public interface MapEngine {
    void addMap(TransPoolMap map);
    TransPoolMap getMap(String mapName);
    boolean isMapExists(String mapName);
    Map<String, TransPoolMap> getMaps();

    Collection<MapDetailsDTO> getMapDetailsDTOs();
}
