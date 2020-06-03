package data.transpool.trip;

import data.jaxb.TransPoolTrip;
import data.transpool.map.Map;
import data.transpool.map.Path;
import exception.TransPoolRunTimeException;
import exception.file.StopNotFoundException;
import exception.file.PathDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Contains a list of strings containing the stop names in the route.
 * Contains a list of used paths in the string.
 */
public class Route {
    private ObservableList<String> route;
    private List<Path> usedPaths = new ArrayList<>();

    public Route(TransPoolTrip JAXBTransPoolTrip) throws PathDoesNotExistException {
        route = FXCollections.observableArrayList();
        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (String str : routeArray) {
            route.add(str.trim());
        }
        initUsedPaths();
    }

    /**
     * Keeps a copy of all used paths in the route.
     * @throws PathDoesNotExistException - If the path does not exist.
     */
    private void initUsedPaths() throws PathDoesNotExistException {
        for (int i = 0; i < route.size() - 1; i++) {
            Path foundPath = Map.getPathBySourceAndDestination(route.get(i), route.get(i + 1));
            if (foundPath == null) {
                throw new PathDoesNotExistException(route.get(i), route.get(i + 1));
            }
            usedPaths.add(new Path(foundPath));
        }
    }

    /**
     * Gets the sub-route of the source and destination names and returns it as a path list.
     * @param source - The source of the sub-route.
     * @param destination - The destination of the sub-route.
     * @return List of used paths.
     * @throws TransPoolRunTimeException - This function is only used internally with existing sources and destinations.
     *                                     Should not throw a runtime exception.
     */
    public List<Path> getSubRouteAsPathList(String source, String destination) {
        int sourceIndex = getIndexByStopName(source);
        int destinationIndex = getIndexByStopName(destination);
        if (sourceIndex < 0 || destinationIndex < 0) {
            throw new TransPoolRunTimeException();
        }
        return new ArrayList<>(usedPaths.subList(sourceIndex, destinationIndex));
    }

    /**
     *
     * @param source - The name of the source stop of the route.
     * @param destination - The name of the destination stop of the route.
     * @return true of there is such a route from source to destination;
     * @throws StopNotFoundException - if one of the names of the stops was not found in the map.
     */
    public boolean containsSubRoute(String source, String destination) {
        int sourceIndex = getIndexByStopName(source);
        int destinationIndex = getIndexByStopName(destination);

        return (sourceIndex < destinationIndex && sourceIndex >= 0);

    }


    public int getNumberOfStops() {
        return route.size();
    }

    public String getStopNameByIndex(int stopIndex) {
        return route.get(stopIndex);
    }

    public int getIndexByStopName(String stopName) {
        for (int i = 0; i < route.size(); i++) {
            if (route.get(i).equals(stopName)) {
                return i;
            }
        }
        return -1;
}

    public ObservableList<String> getRoute() {
        return route;
    }

    public List<Path> getUsedPaths() {
        return usedPaths;
    }

    @Override
    public String toString() {
        int i;
        StringBuilder routeString = new StringBuilder();
        for (i = 0; i < route.size() - 1; i++) {
            routeString.append(route.get(i));
            routeString.append(" -> ");
        }
        routeString.append(route.get(i));
        return routeString.toString();
    }
}