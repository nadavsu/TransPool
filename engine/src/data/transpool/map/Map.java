package data.transpool.map;

import data.jaxb.MapDescriptor;
import exception.file.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * The map.
 * Contains a list of stops, a list of paths, and a matrix containing the stops' positions on the map.
 */
public class Map {

    private final static int MIN_MAP_SIZE = 6;
    private final static int MAX_MAP_SIZE = 100;

    private int width;
    private int length;
    private MapMatrix mapMatrix;

    private static java.util.Map<String, Stop> allStops = new HashMap<>();
    private static List<Path> allPaths = new ArrayList<>();

    /**
     * Constructor for creating a map out of the JAXB generated classes.
     * @param JAXBMap - JAXB Generated map.
     * @throws TransPoolDataException - Thrown if there's a problem with the data inside the TP data file.
     */
    public Map(MapDescriptor JAXBMap) throws TransPoolDataException {
        setWidth(JAXBMap.getMapBoundries().getWidth());
        setLength(JAXBMap.getMapBoundries().getLength());

        initAllStops(JAXBMap);
        initAllPaths(JAXBMap);
        mapMatrix = new MapMatrix(JAXBMap);
    }

    /**
     * Initializer for creating a map of stops from JAXB generated stops list.
     * The function checks if the stop already exists, if so an StopNameDuplicationException is thrown,
     * otherwise the stop is added to the hashmap.
     * @param JAXBMap - The map containing the stops.
     * @throws StopNameDuplicationException - thrown if there is a duplication stop.
     */
    private void initAllStops(MapDescriptor JAXBMap) throws StopNameDuplicationException {
        List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for(data.jaxb.Stop stop : JAXBStopsList) {
            if (allStops.containsKey(stop.getName())) {
                throw new StopNameDuplicationException(stop.getName());
            }
            allStops.put(stop.getName(), new Stop(stop));
        }
    }

    /**
     * Initializer for creating a list of paths from JAXB generated path classes.
     * The function checks if the path exists, or if there is a duplicated path. If so, an exception is thrown,
     * otherwise the path is added to allPaths.
     * @param JAXBMap - The JAXB generated path.
     * @throws TransPoolDataException - thrown if there's a problem with the data inside the JAXB classes/file.
     */
    private void initAllPaths(MapDescriptor JAXBMap) throws PathDuplicationException, PathDoesNotExistException {
        List<data.jaxb.Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (data.jaxb.Path JAXBPath : JAXBPathList) {
            Path transpoolPath = new Path(JAXBPath);
            String source = JAXBPath.getFrom();
            String destination = JAXBPath.getTo();
            if (!allStops.containsKey(source)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (!allStops.containsKey(destination)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (allPaths.contains(transpoolPath)) {
                throw new PathDuplicationException(source, destination);
            }
            if (!JAXBPath.isOneWay()) {
                Path swappedPath = new Path(transpoolPath);
                swappedPath.swapDirection();
                allPaths.add(swappedPath);
            }
            allPaths.add(transpoolPath);
        }
    }

    /**
     * Finds the path with the given source and destination stop names.
     * @param source - The name of the source stop.
     * @param destination - The name of the destination stop.
     * @return A reference to the path from the list of all paths if found, null otherwise.
     */
    public static Path getPathBySourceAndDestination(String source, String destination) {
        Predicate<Path> sourceDestinationMatchPredicate = p ->
                p.getDestination().equals(destination) && p.getSource().equals(source);

        return allPaths
                .stream()
                .filter(sourceDestinationMatchPredicate)
                .findFirst()
                .orElse(null);
    }

    public boolean containsStop(String name) {
        return allStops.containsKey(name);
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

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public MapMatrix getMapMatrix() {
        return mapMatrix;
    }

    public java.util.Map<String, Stop> getAllStops() {
        return allStops;
    }

    public List<Path> getAllPaths() {
        return allPaths;
    }

    /**
     * 2 Dimensional array of strings containing the name of the stop in each coordinate.
     */
    public class MapMatrix {
        private String[][] mapMatrix = new String[Map.MAX_MAP_SIZE][Map.MAX_MAP_SIZE];

        public MapMatrix(MapDescriptor JAXBMap) throws StopOutOfBoundsException, StopCoordinatesDuplicationException {
            List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
            for (data.jaxb.Stop stop : JAXBStopsList) {
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

        public String[][] getMapMatrix() {
            return mapMatrix;
        }
    }
}
