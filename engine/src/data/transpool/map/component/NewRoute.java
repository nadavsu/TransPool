package data.transpool.map.component;

import data.jaxb.TransPoolTrip;
import data.transpool.map.MapGraph;
import javafx.collections.FXCollections;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewRoute {
/*    private List<Stop> route;
    private List<Path> usedPaths;

    public NewRoute(TransPoolTrip JAXBTransPoolTrip, MapGraph map) {
        this.route = FXCollections.observableArrayList();
        this.usedPaths = new ArrayList<>();

        initStopsList(JAXBTransPoolTrip, map.getAllStops());
        initUsedPaths(JAXBTransPoolTrip, map);
    }

    private void initStopsList(TransPoolTrip JAXBTransPoolTrip, Map<String, Stop> allStops) {
        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (String str : routeArray) {
            route.add(new Stop(allStops.get(str)));
        }
    }

    private void initUsedPaths(TransPoolTrip JAXBTransPoolTrip, MapGraph map) {
        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        for (int i = 0; i < routeArray.length - 1; i++) {
            Path currentPath = map.getPath(routeArray[i], routeArray[i + 1]);
            usedPaths.add(new Path(currentPath));
        }
    }*/

}
