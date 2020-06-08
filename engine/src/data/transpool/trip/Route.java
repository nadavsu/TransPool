package data.transpool.trip;

import data.jaxb.TransPoolTrip;
import data.transpool.map.BasicMapData;
import data.transpool.map.Path;
import exception.data.InvalidRouteException;
import exception.TransPoolRunTimeException;
import exception.data.PathDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of strings containing the stop names in the route.
 * Contains a list of used paths in the string.
 */
public class Route {
    private ObservableList<String> route;
    private List<Path> usedPaths;


    public Route(ObservableList<String> route) throws PathDoesNotExistException, InvalidRouteException {
        if (route.size() < 2) {
            throw new InvalidRouteException();
        }
        this.usedPaths = new ArrayList<>();
        this.route = FXCollections.observableArrayList(route);

        initUsedPaths();
    }

    public Route(TransPoolTrip JAXBTransPoolTrip) throws PathDoesNotExistException {
        this.route = FXCollections.observableArrayList();
        this.usedPaths = new ArrayList<>();

        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (String str : routeArray) {
            route.add(str.trim());
        }

        initUsedPaths();
    }

    public Route(Route other) {
        this.route = FXCollections.observableArrayList(other.getRoute());
        this.usedPaths  = new ArrayList<>(other.getUsedPaths());
    }

    /**
     * Keeps a copy of all used paths in the route.
     * @throws PathDoesNotExistException - If the path does not exist.
     */
    private void initUsedPaths() throws PathDoesNotExistException {
        for (int i = 0; i < route.size() - 1; i++) {
            Path foundPath = BasicMapData.getPathBySourceAndDestination(route.get(i), route.get(i + 1));
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
    public static List<Path> asPathList(Route route, String source, String destination) {
        int sourceIndex = route.getIndexByStopName(source);
        int destinationIndex = route.getIndexByStopName(destination);
        if (sourceIndex < 0 || destinationIndex < 0) {
            throw new TransPoolRunTimeException();
        }
        return new ArrayList<>(route.usedPaths.subList(sourceIndex, destinationIndex));
    }

    public Route subRoute(String sourceStopName, String destinationStopName) {
        try {
            int sourceIndex = getIndexByStopName(sourceStopName);
            int destinationIndex = getIndexByStopName(destinationStopName);
            if (sourceIndex < 0 || destinationIndex < 0) {
                throw new TransPoolRunTimeException();
            }
            return new Route(FXCollections.observableArrayList(route.subList(sourceIndex, destinationIndex + 1)));
        } catch (PathDoesNotExistException | InvalidRouteException ignored) {
            return null;
        }
    }

    /**
     *
     * @param source - The name of the source stop of the route.
     * @param destination - The name of the destination stop of the route.
     * @return true of there is such a route from source to destination;
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

    public String getFirstStopName() {
        return route.get(0);
    }

    public String getLastStopName() {
        return route.get(route.size() - 1);
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