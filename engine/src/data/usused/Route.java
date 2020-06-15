/*
package data.transpool.trip;

import data.jaxb.TransPoolTrip;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.data.TripOffer;
import exception.data.InvalidRouteException;
import exception.TransPoolRunTimeException;
import exception.data.PathDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.*;

*/
/**
 * Contains a list of strings containing the stop names in the route.
 * Contains a list of used paths in the string.
 *//*

public class Route {
    private ObservableList<Stop> stops;
    private List<Path> usedPaths;

    public Route(ObservableList<String> stopNamesList, BasicMap map) throws PathDoesNotExistException, InvalidRouteException {
        if (stopNamesList.size() < 2) {
            throw new InvalidRouteException();
        }
        this.usedPaths = new ArrayList<>();
        this.stops = FXCollections.observableArrayList();
        initStopsList(stopNamesList, map.getAllStops());
        initUsedPaths(map);
    }

    public Route(List<Stop> stopList, BasicMap map) throws InvalidRouteException, PathDoesNotExistException{
        if (stopList.size() < 2) {
            throw new InvalidRouteException();
        }
        this.usedPaths = new ArrayList<>();
        this.stops = FXCollections.observableArrayList(stopList);
        initUsedPaths(map);
    }

    public Route(Route other) {
        this.stops = FXCollections.observableArrayList(other.getStops());
        this.usedPaths  = new ArrayList<>(other.getUsedPaths());
    }

    public Route(TransPoolTrip JAXBTransPoolTrip, BasicMap map) throws PathDoesNotExistException {
        this.stops = FXCollections.observableArrayList();
        this.usedPaths = new ArrayList<>();

        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        List<String> stopNamesList = Arrays.asList(routeArray);
        initStopsList(stopNamesList, map.getAllStops());
        initUsedPaths(map);
    }

    private void initStopsList(List<String> stopNamesList, Map<String, Stop> allStops) {
        for (String str : stopNamesList) {
            stops.add(new Stop(allStops.get(str)));
        }
    }

    */
/**
     * Keeps a copy of all used paths in the route.
     * @throws PathDoesNotExistException - If the path does not exist.
     *//*

    private void initUsedPaths(BasicMap map) throws PathDoesNotExistException {
        for (int i = 0; i < stops.size() - 1; i++) {
            Path foundPath = map.getPath(stops.get(i), stops.get(i + 1));
            if (foundPath == null) {
                throw new PathDoesNotExistException(stops.get(i), stops.get(i + 1));
            }
            usedPaths.add(new Path(foundPath));
        }
    }

*/
/*    private void initUsedPaths(List<Stop> stopsList, BasicMap map) {
        for (int i = 0; i < stopsList.size() - 1; i++) {
            Path foundPath = map.getPath(stopsList.get(i), stopsList.get(i + 1));
            if (foundPath == null) {
                throw new PathDoesNotExistException(stopsList.get(i), stopsList.get(i + 1));
            }
            usedPaths.add(new Path(foundPath));
        }
    }*//*

*/
/*
    private void initUsedPaths() throws PathDoesNotExistException {
        for (int i = 0; i < route.size() - 1; i++) {
            Path foundPath = BasicMapData.getPathBySourceAndDestination(route.get(i), route.get(i + 1));
            if (foundPath == null) {
                throw new PathDoesNotExistException(route.get(i), route.get(i + 1));
            }
            usedPaths.add(new Path(foundPath));
        }
    }*//*

*/
/*
    private void initUsedStops(List<Stop> stopList) {
        stopList.forEach(stop -> {
            route.add(stop.getName());
        });
    }*//*


    public int getLength() {
        return stops.size();
    }

    public Stop getStop(int i) {
        return stops.get(i);
    }

    public Path getPath(int i) {
        return usedPaths.get(i);
    }

    */
/**
     * Gets the sub-route of the source and destination names and returns it as a path list.
     * @param  - The source of the sub-route.
     * @param  - The destination of the sub-route.
     * @return List of used paths.
     * @throws TransPoolRunTimeException - This function is only used internally with existing sources and destinations.
     *                                     Should not throw a runtime exception.
     *//*

*/
/*
    public static List<Path> asPathList(Route route, String source, String destination) {
        int sourceIndex = route.getIndexByStopName(source);
        int destinationIndex = route.getIndexByStopName(destination);
        if (sourceIndex < 0 || destinationIndex < 0) {
            throw new TransPoolRunTimeException();
        }
        return new ArrayList<>(route.usedPaths.subList(sourceIndex, destinationIndex));
    }
*//*


    */
/**
     *
     * @param source - The name of the source stop of the route.
     * @param destination - The name of the destination stop of the route.
     * @return true of there is such a route from source to destination;
     *//*

    public boolean containsSubRoute(String source, String destination) {
        int sourceIndex = getIndexByStopName(source);
        int destinationIndex = getIndexByStopName(destination);

        return (sourceIndex < destinationIndex && sourceIndex >= 0);

    }

    public int getIndexByStopName(String stopName) {
        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).getName().equals(stopName)) {
                return i;
            }
        }
        return -1;
}

    public ObservableList<Stop> getStops() {
        return stops;
    }

    public List<Path> getUsedPaths() {
        return usedPaths;
    }

    public Stop getFirstStopName() {
        return stops.get(0);
    }

    public Stop getLastStopName() {
        return stops.get(stops.size() - 1);
    }

    @Override
    public String toString() {
        int i;
        StringBuilder routeString = new StringBuilder();
        for (i = 0; i < stops.size() - 1; i++) {
            routeString.append(stops.get(i));
            routeString.append(" -> ");
        }
        routeString.append(stops.get(i));
        return routeString.toString();
    }
}*/
