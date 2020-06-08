package data.transpool.map;

import java.util.ArrayList;
import java.util.List;

public interface Graph<V,E> {
    void newConnection(V v);
    ArrayList<List<E>> getAllPossibleRoutes(E source, E destination);
    ArrayList<List<E>> getGraph();
}
