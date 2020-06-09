package data.transpool.map;

import data.transpool.map.util.Graph;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;

public interface MapGraph extends BasicMap, Graph<Stop, Path> {

}
