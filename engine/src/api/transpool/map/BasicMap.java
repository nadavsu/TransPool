package api.transpool.map;

import api.transpool.map.component.Path;
import api.transpool.map.component.PathDTO;
import api.transpool.map.component.Stop;
import api.transpool.map.component.StopDTO;

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

    List<StopDTO> getAllStopsDetails();
    List<PathDTO> getAllPathsDetails();
    BasicMapDTO getMapDetails();

}
