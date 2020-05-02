package data.transpool.structures;

import data.jaxb.TransPoolTrip;
import data.transpool.TransPoolPath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Route {
    private List<String> route = new ArrayList<>();

    public Route(TransPoolTrip JAXBTransPoolTrip) {
        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (String str : routeArray) {
            route.add(str.trim());
        }
    }

    public TransPoolPath getPathOfSource(int index) {
        return TransPoolMap
                .getAllPaths()
                .getPathBySourceAndDestination(route.get(index), route.get(index + 1));
    }

    public List<TransPoolPath> getSubRouteAsPathList(String source, String destination) {
        List<TransPoolPath> subRoutePathList = new ArrayList<>();
        int sourceIndex = getIndexByStopName(source);
        int destinationIndex = getIndexByStopName(destination);

        for (int i = sourceIndex; i < destinationIndex - 1; i++) {
            subRoutePathList.add(getPathOfSource(i));
        }
        return subRoutePathList;
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

    @Override
    public String toString() {
        int i;
        StringBuilder routeString = new StringBuilder();
        for (i = 0; i < route.size() - 1; i++) {
            routeString.append(route.get(i));
            routeString.append("->");
        }
        routeString.append(route.get(i));
        return routeString.toString();
    }
}
