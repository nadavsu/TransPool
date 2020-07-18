package data.transpool.map;

import com.fxgraph.graph.Graph;
import data.transpool.Updatable;

public interface MapGraph extends BasicMap, Updatable {
    void createMapModel(Graph graph);
}
