package data.transpool.map;

import data.jaxb.MapDescriptor;
import data.jaxb.Stop;
import data.transpool.structures.TransPoolPaths;
import data.transpool.structures.TransPoolStops;
import exceptions.file.MapDimensionsException;
import exceptions.file.StopCoordinatesDuplicationException;
import exceptions.file.StopOutOfBoundsException;
import exceptions.file.TransPoolDataFileException;

import java.util.List;

/**
 * The map.
 * Contains a list of stops, a list of paths, and a matrix containing the stops' positions on the map.
 */
public class Map {

    public final static int MIN_MAP_SIZE = 6;
    public final static int MAX_MAP_SIZE = 100;

    private static int width;
    private static int length;
    private static MapMatrix mapMatrix;

    private static TransPoolStops allStops;
    private static TransPoolPaths allPaths;

    /**
     * Constructor for creating a map out of the JAXB generated classes.
     * @param JAXBMap - JAXB Generated map.
     * @throws TransPoolDataFileException - Thrown if there's a problem with the data inside the TP data file.
     */
    public Map(MapDescriptor JAXBMap) throws TransPoolDataFileException {
        setWidth(JAXBMap.getMapBoundries().getWidth());
        setLength(JAXBMap.getMapBoundries().getLength());
        allStops = new TransPoolStops(JAXBMap);
        mapMatrix = new MapMatrix(JAXBMap);
        allPaths = new TransPoolPaths(JAXBMap);
    }


    private void setWidth(int width) throws MapDimensionsException {
        if (width < MIN_MAP_SIZE || width > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        Map.width = width;
    }

    private void setLength(int length) throws MapDimensionsException {
        if (length > MAX_MAP_SIZE || length < MIN_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        Map.length = length;
    }

    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
    }

    public static MapMatrix getMapMatrix() {
        return mapMatrix;
    }

    public static TransPoolStops getAllStops() {
        return allStops;
    }

    public static TransPoolPaths getAllPaths() {
        return allPaths;
    }

    /**
     * 2 Dimensional array of strings containing the name of the stop in each coordinate.
     */
    public static class MapMatrix {
        private String[][] mapMatrix = new String[Map.MAX_MAP_SIZE][Map.MAX_MAP_SIZE];

        public MapMatrix(MapDescriptor JAXBMap) throws StopOutOfBoundsException, StopCoordinatesDuplicationException {
            List<Stop> JAXBStopsList = JAXBMap.getStops().getStop();
            for (Stop stop : JAXBStopsList) {
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
