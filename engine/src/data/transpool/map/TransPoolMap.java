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
    private Map<String, List<Stop>> mapGraph;
    private Map<String, Stop> stopMap;          //Maybe this should be a list.
    private List<Path> pathList;
    private Set<Coordinates> stopCoordinates = new HashSet<>();

    public TransPoolMap() {
        stopMap = new HashMap<>();
        pathList = new ArrayList<>();
        width = MIN_MAP_SIZE;
        length = MIN_MAP_SIZE;
    }

    public TransPoolMap(MapDescriptor JAXBMap) throws TransPoolDataException {
        stopMap = new HashMap<>();
        pathList = new ArrayList<>();
        mapGraph = new HashMap<>();

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

        for (Map.Entry<String, Stop> stringStopEntry : stopMap.entrySet()) {
            mapGraph.put(stringStopEntry.getKey(), new ArrayList<>());
        }
        initMapGraph();

    }

    public void initMapGraph() throws StopNotFoundException {
        for (Path path : pathList) {
            Stop sourceStop = getStop(path.getSource());
            Stop destinationStop = getStop(path.getDestination());

            mapGraph.get(path.getSource()).add(destinationStop);
            if (!path.isOneWay()) {
                mapGraph.get(path.getDestination()).add(sourceStop);
            }
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

    public Map<String, List<Stop>> getMapGraph() {
        return mapGraph;
    }

    public Stop getStop(String stopName) throws StopNotFoundException {
        Stop foundStop = stopMap.get(stopName);
        if (foundStop == null) {
            throw new StopNotFoundException(stopName);
        }
        return foundStop;
    }

    private void addStop(Stop stop) throws StopOutOfBoundsException, StopDuplicationException {
        if (stopMap.containsKey(stop.getName())) {
            throw new StopDuplicationException();
        }
        if (!stopCoordinates.add(stop.getCoordinates())) {
            throw new StopDuplicationException();
        }
        if (!stop.isInBoundsOf(width, length)) {
            throw new StopOutOfBoundsException();
        }
        stopMap.put(stop.getName(), stop);
    }

    private void addPath(Path path) throws StopNotFoundException {
        String destinationName = path.getDestination();
        String sourceName = path.getSource();
        if (!stopMap.containsKey(destinationName)) {
            throw new StopNotFoundException(destinationName);
        }
        if (!stopMap.containsKey(sourceName)) {
            throw new StopNotFoundException(sourceName);
        }
        pathList.add(path);
    }

    public boolean doesPathExist(String source, String destination) {
        List<Stop> sourcePaths = mapGraph.get(source);
        for (Stop stop : sourcePaths) {
            if (destination.equals(stop.getName())) {
                return true;
            }
        }
        return false;
    }
}
