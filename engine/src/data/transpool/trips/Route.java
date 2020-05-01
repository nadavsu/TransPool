package data.transpool.trips;

import data.transpool.map.TransPoolMap;
import data.transpool.map.Path;
import exceptions.data.PathDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private List<Path> route = new ArrayList<>();

    public Route(List<Path> route) {
        this.route = route;
    }

    public Route(data.jaxb.Route JAXBRoute, TransPoolMap map) throws PathDoesNotExistException {
        String[] JAXBRouteStops = JAXBRoute.getPath().split(",");

        for (int i = 0; i < JAXBRouteStops.length - 1; i++) {
            String source = JAXBRouteStops[i].trim();
            String destination = JAXBRouteStops[i + 1].trim();
            try {
                route.add(map.getPathByStopNames(source, destination));
            } catch (NullPointerException e) {
                //Maybe Invalid route exception?
                throw new PathDoesNotExistException(source, destination);
            }
        }
    }

    public List<Path> getRoute() {
        return route;
    }

    public void setRoute(List<Path> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        StringBuilder routeBuilder = new StringBuilder("Route: ");
        for (Path p : route) {
            routeBuilder.append(p.getSource()).append(" -> ");
        }
        routeBuilder.append(route.get(route.size() - 1).getDestination());
        return routeBuilder.toString();
    }
}
