package data.transpool.map;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;

import java.util.List;
import java.util.Map;

public interface BasicMap {
    int getMapWidth();
    int getMapLength();

    Map<String, Stop> getAllStops();
    List<Stop> getAllStopsAsList();
    Stop getStop(String stopName);
    int getNumberOfStops();
    boolean containsStop(String stopName);

    List<Path> getAllPaths();
    Path getPath(Stop source, Stop destination);
    Path getPath(String source, String destination);
    boolean containsPath(String source, String destination);

}
