package data.transpool.util;

import javafx.util.Pair;

import java.util.List;

public interface WeightedGraph<V, E, W> {
    void newConnection(E E, W w);
    List<List<Pair<V, W>>> getAllPossibleRoutes(V source, V destination);
    List<List<Pair<V, W>>> getGraph();
}
