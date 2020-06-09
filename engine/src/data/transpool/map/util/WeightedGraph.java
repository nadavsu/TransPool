package data.transpool.map.util;

import data.transpool.map.component.Path;
import data.transpool.trip.offer.TripOffer;
import javafx.util.Pair;

import java.util.List;

public interface WeightedGraph<V, E, W> {
    void newConnection(E E, W w);
    List<List<Pair<V, W>>> getAllPossibleRoutes(V source, V destination) throws Exception;
    List<List<Pair<V, W>>> getGraph();
}
