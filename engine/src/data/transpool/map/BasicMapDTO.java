package data.transpool.map;

import data.transpool.map.component.PathDTO;
import data.transpool.map.component.StopDTO;

import java.util.List;

public class BasicMapDTO {
    private List<StopDTO> allStops;
    private List<PathDTO> allPaths;
    private int width;
    private int length;

    public BasicMapDTO(BasicMap map) {
        this.allStops = map.getAllStopsDetails();
        this.allPaths = map.getAllPathsDetails();
        this.width = map.getMapWidth();
        this.length = map.getMapLength();
    }

    public List<StopDTO> getAllStops() {
        return allStops;
    }

    public List<PathDTO> getAllPaths() {
        return allPaths;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
