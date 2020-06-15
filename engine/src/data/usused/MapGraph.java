package data.usused;

import data.transpool.map.BasicMap;
import data.transpool.util.Graph;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;

public interface MapGraph extends BasicMap, Graph<Stop, Path> {

}
