package data.transpool.map;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import exception.data.StopNotFoundException;

import java.util.List;
import java.util.Map;

public interface BasicMap {

    int getMapWidth();
    int getMapLength();

    boolean containsStop(String stopName);
    Map<String, Stop> getAllStops();
    List<Stop> getAllStopsAsList();
    List<String> getAllStopNamesAsList();
    Stop getStop(String stopName);
    int getNumberOfStops();

    List<Path> getAllPaths();
    boolean containsPath(String source, String destination);
    Path getPath(Stop source, Stop destination);
    Path getPath(String source, String destination);
    int getNumberOfPaths();

}
