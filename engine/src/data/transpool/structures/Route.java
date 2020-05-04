package data.transpool.structures;

import data.jaxb.TransPoolTrip;
import data.transpool.map.Map;
import data.transpool.map.Path;
import exceptions.file.PathDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of strings containing the stop names in the route.
 * Contains a list of used paths in the string.
 */
public class Route {
    private List<String> route = new ArrayList<>();
    private List<Path> usedPaths = new ArrayList<>();

    public Route(TransPoolTrip JAXBTransPoolTrip) throws PathDoesNotExistException {
        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (String str : routeArray) {
            route.add(str.trim());
        }
        initUsedPaths();
    }

    private void initUsedPaths() throws PathDoesNotExistException {
        for (int i = 0; i < route.size() - 1; i++) {
            usedPaths.add(Map
                    .getAllPaths()
                    .getPathBySourceAndDestination(route.get(i), route.get(i + 1)));
        }
    }

    public List<Path> getSubRouteAsPathList(String source, String destination) {
        int sourceIndex = getIndexByStopName(source);
        int destinationIndex = getIndexByStopName(destination);
        return new ArrayList<>(usedPaths.subList(sourceIndex, destinationIndex));
    }

    public boolean containsSubRoute(String source, String destination) {
        int sourceIndex = getIndexByStopName(source);
        int destinationIndex = getIndexByStopName(destination);

        return (sourceIndex < destinationIndex) && (sourceIndex >= 0);

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

    public List<String> getRoute() {
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