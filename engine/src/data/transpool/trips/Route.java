package data.transpool.trips;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Route {
    private List<String> route;

    public Route(List<String> route) {
        this.route = route;
    }

    public Route(data.jaxb.Route route) {
        String[] stops = route.getPath().split(",");
        List<String> fixedLengthRoute = Arrays.asList(stops);
        this.route = fixedLengthRoute
                .stream()
                .map(String::trim)
                .collect(Collectors.toList());

    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Route: " + route;
    }
}
