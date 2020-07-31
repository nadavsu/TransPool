package api;

import data.transpool.MapDetailsDTO;
import data.transpool.SingleMapEngine;

import java.util.Collection;
import java.util.Map;

public interface MapsEngine {
    void addMap(SingleMapEngine map);
    SingleMapEngine getMap(String mapName);
    boolean isMapExists(String mapName);
    Map<String, SingleMapEngine> getMaps();

    Collection<MapDetailsDTO> getMapDetailsDTOs();
}
