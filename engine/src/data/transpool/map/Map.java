package data.transpool.map;

import data.jaxb.MapDescriptor;
import exceptions.data.PathDoesNotExistException;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.MapDimensionsException;
import exceptions.data.StopDuplicationException;
import exceptions.data.StopOutOfBoundsException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {
    private static final int MAX_MAP_SIZE = 100;
    private static final int MIN_MAP_SIZE = 6;

    public int width;
    public int length;
    private Set<Stop> stopSet;
    private List<Path> pathList;

    private Set<Coordinates> stopCoordinates = new HashSet<>();
    private Set<String> stopNames = new HashSet<>();

    public Map() {
        stopSet = new HashSet<>();
        pathList = new ArrayList<>();
        width = MIN_MAP_SIZE;
        length = MIN_MAP_SIZE;
    }

    public Map(MapDescriptor JAXBMap) throws TransPoolDataException {
        stopSet = new HashSet<>();
        pathList = new ArrayList<>();
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

    public void setStopSet(Set<Stop> stopSet) {
        this.stopSet = stopSet;
    }

    public Stop getStop(String stopName) throws StopNotFoundException {
        Stop theStop = null;
        for (Stop stop : stopSet) {
            if (stop.getName().equals(stopName)) {
                theStop = stop;
            }
        }
        if (null == theStop) {
            throw new StopNotFoundException(stopName);
        }
        return theStop;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public void setPathList(List<Path> pathList) {
        this.pathList = pathList;
    }

    public void addStop(Stop stop) throws StopOutOfBoundsException, StopDuplicationException {
        if (!stopNames.add(stop.getName())) {
            throw new StopDuplicationException();
        }
        if (!stopCoordinates.add(stop.getCoordinates())) {
            throw new StopDuplicationException();
        }
        if (!stop.isInBoundsOf(width, length)) {
            throw new StopOutOfBoundsException();
        }
        stopSet.add(stop);
    }

    public void addPath(Path path) throws PathDoesNotExistException {
        if (!stopNames.contains(path.getDestination()) || !stopNames.contains(path.getSource())) {
            throw new PathDoesNotExistException(path.getSource(), path.getDestination());
        }
        pathList.add(path);
    }
}
