package data.transpool.map;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private int width;
    private int length;
    private List<Stop> stops;   //todo: change this to data.transpool.map
    private List<Path> paths;

    public Map() {
        stops = new ArrayList<>();
        paths = new ArrayList<>();
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
