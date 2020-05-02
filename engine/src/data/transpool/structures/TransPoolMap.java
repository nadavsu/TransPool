package data.transpool.structures;

import data.jaxb.MapDescriptor;
import data.jaxb.Stop;
import exceptions.data.*;

import java.util.List;

public class TransPoolMap {

    public final static int MIN_MAP_SIZE = 6;
    public final static int MAX_MAP_SIZE = 100;

    private static int width;
    private static int length;
    private static MapMatrix mapMatrix;

    private static TransPoolStops allStops;
    private static TransPoolPaths allPaths;

    public TransPoolMap(MapDescriptor JAXBMap) throws TransPoolDataException {
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
        TransPoolMap.width = width;
    }

    private void setLength(int length) throws MapDimensionsException {
        if (length > MAX_MAP_SIZE || length < MIN_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        TransPoolMap.length = length;
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

    public class MapMatrix {
        private String[][] mapMatrix = new String[TransPoolMap.MAX_MAP_SIZE][TransPoolMap.MAX_MAP_SIZE];

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
