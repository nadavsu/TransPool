package data.usused;

import javafx.util.Pair;

import java.util.List;

public interface WeightedGraph<V, E, W> {
    void newConnection(E e, W w);
    List<List<Pair<V, W>>> getAllPossibleRoutes(V source, V destination);
}
