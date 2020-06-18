package data.transpool.map.component;

import api.components.map.course.transpool.graph.component.road.ArrowedEdge;
import api.components.map.course.transpool.graph.component.station.StationManager;
import api.components.map.course.transpool.graph.component.station.StationNode;
import api.components.map.course.transpool.graph.layout.MapGridLayout;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import data.jaxb.MapDescriptor;
import data.transpool.map.BasicMapData;
import exception.data.TransPoolDataException;

public class MapGraphModel extends BasicMapData {

    private Model mapGraphModel;

    public MapGraphModel(MapDescriptor JAXBMap) throws TransPoolDataException {
        super(JAXBMap);
    }



    public void createMapModel(Graph graph) {
        graph.beginUpdate();
        mapGraphModel = graph.getModel();
        StationManager stationManager = createStations(mapGraphModel);
        createEdges(stationManager);
        graph.endUpdate();
        graph.layout(new MapGridLayout(stationManager));
    }

    private StationManager createStations(Model model) {
        StationManager sm = new StationManager(StationNode::new);

        allStops.forEach((string, stop) -> {
            StationNode station = sm.getOrCreate(stop.getX(), stop.getY());
            station.setName(string);
            station.setDetailsSupplier(stop::getDetails);
            model.addCell(station);
        });

        return sm;
    }

    private void createEdges(StationManager sm) {
        allPaths.forEach(path -> {
            int sourceX = path.getSourceStop().getX();
            int sourceY = path.getSourceStop().getY();
            int destinationX = path.getDestinationStop().getX();
            int destinationY = path.getDestinationStop().getY();
            mapGraphModel.addEdge(
                    new ArrowedEdge(sm.getOrCreate(sourceX, sourceY), sm.getOrCreate(destinationX, destinationY))
            );
        });
    }

    public void update() {
        allStops.forEach((s, stop) -> stop.clearDetails());
    }
}
