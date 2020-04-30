package data.transpool.map;

import data.jaxb.MapDescriptor;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.MapDimensionsException;
import exceptions.data.StopDuplicationException;
import exceptions.data.StopOutOfBoundsException;

import java.util.*;

public class TransPoolMap {
    private static final int MAX_MAP_SIZE = 100;
    private static final int MIN_MAP_SIZE = 6;

    private int width;
    private int length;

    private List<Stop> stopMap = new ArrayList<>();
    private List<Path> pathList = new ArrayList<>();

    private Set<String> stopNameSet = new HashSet<>();
    private Set<Coordinates> stopCoordinates = new HashSet<>();

    public TransPoolMap() {
        width = MIN_MAP_SIZE;
        length = MIN_MAP_SIZE;
    }

    public TransPoolMap(MapDescriptor JAXBMap) throws TransPoolDataException {
        setWidth(JAXBMap.getMapBoundries().getWidth());
        setLength(JAXBMap.getMapBoundries().getLength());

        List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for (data.jaxb.Stop JAXBStop : JAXBStopsList) {
            addStop(new Stop(JAXBStop));
        }

        List<data.jaxb.Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (data.jaxb.Path JAXBPath : JAXBPathList) {
            addPath(new Path(JAXBPath));
        }

    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) throws MapDimensionsException {
        if (width < MIN_MAP_SIZE || width > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    private void setLength(int length) throws MapDimensionsException {
        if (length < MIN_MAP_SIZE || length > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.length = length;
    }

    public Set<Coordinates> getStopCoordinatesSet() {
        return stopCoordinates;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public Stop getStop(String stopName) throws StopNotFoundException {
        if (!stopNameSet.contains(stopName)) {
            throw new StopNotFoundException(stopName);
        }

        return stopMap
                .stream()
                .filter(s -> s.getName().equals(stopName))
                .findAny()
                .orElse(null);
    }

    private void addStop(Stop stop) throws StopOutOfBoundsException, StopDuplicationException {
        if (!stopNameSet.add(stop.getName())) {
            throw new StopDuplicationException();
        }
        if (!stopCoordinates.add(stop.getCoordinates())) {
            throw new StopDuplicationException();
        }
        if (!stop.isInBoundsOf(width, length)) {
            throw new StopOutOfBoundsException();
        }
        stopMap.add(stop);
    }

    private void addPath(Path path) throws StopNotFoundException {
        String destinationName = path.getDestination();
        String sourceName = path.getSource();
        if (!stopNameSet.contains(destinationName)) {
            throw new StopNotFoundException(destinationName);
        }
        if (!stopNameSet.contains(sourceName)) {
            throw new StopNotFoundException(sourceName);
        }
        pathList.add(path);
        if (!path.isOneWay()) {
            pathList.add(
                    new Path(path.getDestination(), path.getSource(), path.getLength(),
                            path.isOneWay(), path.getFuelConsumption(), path.getSpeedLimit())
            );
        }
    }

    public boolean doesPathExist(String source, String destination) {
        Path foundPath = pathList
                .stream()
                .filter(path -> path.getSource().equals(source) && path.getDestination().equals(destination))
                .findAny()
                .orElse(null);
        return foundPath != null;
    }
}
