package data.transpool.map;

import data.generated.MapDescriptor;
import data.generated.TransPool;

import javax.xml.bind.JAXB;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Map {
    private int width;
    private int length;
    private List<Stop> stops;
    private List<Path> paths;

    public Map() {
        stops = new ArrayList<>();
        paths = new ArrayList<>();
        this.width = 0;
        this.length = 0;
    }

    public Map(MapDescriptor JAXBMap) {
        width = JAXBMap.getMapBoundries().getWidth();
        length = JAXBMap.getMapBoundries().getLength();
        stops = JAXBMap
                .getStops()
                .getStop()
                .stream()
                .map(Stop::new)
                .collect(Collectors.toList());
        paths = JAXBMap
                .getPaths()
                .getPath()
                .stream()
                .map(Path::new)
                .collect(Collectors.toList());
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}
