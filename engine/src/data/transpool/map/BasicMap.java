package data.transpool.map;

import java.util.List;
import java.util.Map;

public interface BasicMap {
    int getMapWidth();
    int getMapLength();

    boolean containsStop(String stopName);
    Map<String, Stop> getAllStops();
    List<Stop> getAllStopsAsList();
    Stop getStop(String stopName);

    List<Path> getAllPaths();
    boolean containsPath(String source, String destination);

}
