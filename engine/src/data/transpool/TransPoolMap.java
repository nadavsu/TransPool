package data.transpool;

import data.jaxb.MapDescriptor;
import data.jaxb.Path;
import data.jaxb.Stop;
import exceptions.data.*;
import exceptions.data.PathDuplicationException;

import java.util.*;

public class TransPoolMap {

    private final static int MIN_MAP_SIZE = 6;
    private final static int MAX_MAP_SIZE = 100;

    private int width;
    private int length;
    private String[][] mapMatrix;

    private Map<String, TransPoolStop> stops = new HashMap<>();
    private List<TransPoolPath> paths = new ArrayList<>();

    public TransPoolMap(MapDescriptor JAXBMap) throws TransPoolDataException {
        this.mapMatrix = new String[MAX_MAP_SIZE][MAX_MAP_SIZE];
        setWidth(JAXBMap.getMapBoundries().getWidth());
        setLength(JAXBMap.getMapBoundries().getLength());
        initMapMatrix(JAXBMap);
        initStopsList(JAXBMap);
        initPathsList(JAXBMap);
    }

    private void setWidth(int width) throws MapDimensionsException {
        if (width < MIN_MAP_SIZE || width > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.width = width;
    }

    private void setLength(int length) throws MapDimensionsException {
        if (length > MAX_MAP_SIZE || length < MIN_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.length = length;
    }

    private void initMapMatrix(MapDescriptor JAXBMap) throws StopOutOfBoundsException, StopCoordinatesDuplicationException {
        List<Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for(Stop stop : JAXBStopsList) {
            int x = stop.getX();
            int y = stop.getY();
            if (x > width || y > length) {
                throw new StopOutOfBoundsException(stop.getName());
            }
            if (mapMatrix[y][x] != null) {
                throw new StopCoordinatesDuplicationException(x, y);
            }
            mapMatrix[y][x] = stop.getName();
        }
    }

    private void initStopsList(MapDescriptor JAXBMap) throws StopNameDuplicationException {
        List<Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for(Stop stop : JAXBStopsList) {
            if (stops.containsKey(stop.getName())) {
                throw new StopNameDuplicationException(stop.getName());
            }
            stops.put(stop.getName(), new TransPoolStop(stop));
        }
    }

    private void initPathsList(MapDescriptor JAXBMap) throws TransPoolDataException{
        List<Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (Path JAXBPath : JAXBPathList) {
            TransPoolPath transpoolPath = new TransPoolPath(JAXBPath);
            String source = JAXBPath.getFrom();
            String destination = JAXBPath.getTo();
            if (!stops.containsKey(source) || !stops.containsKey(destination)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (paths.contains(transpoolPath)) {
                throw new PathDuplicationException(source, destination);
            }
            if (!JAXBPath.isOneWay()) {
                TransPoolPath swappedPath = new TransPoolPath(transpoolPath);
                swappedPath.swapDirection();
                paths.add(swappedPath);
            }
            paths.add(transpoolPath);
        }

    }

    public TransPoolPath getPathBySourceAndDestination(String source, String destination) {
        return paths
                .stream()
                .filter(p -> p.getSource().equals(source) &&
                        p.getDestination().equals(destination))
                .findAny()
                .orElse(null);
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public String[][] getMapMatrix() {
        return mapMatrix;
    }

    public Map<String, TransPoolStop> getStops() {
        return stops;
    }

    public List<TransPoolPath> getPaths() {
        return paths;
    }
}
