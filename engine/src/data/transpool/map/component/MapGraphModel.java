package data.transpool.map.component;

import api.components.map.course.transpool.graph.component.coordinate.CoordinateNode;
import api.components.map.course.transpool.graph.component.coordinate.CoordinatesManager;
import api.components.map.course.transpool.graph.component.details.StationDetailsDTO;
import api.components.map.course.transpool.graph.component.road.ArrowedEdge;
import api.components.map.course.transpool.graph.component.station.StationManager;
import api.components.map.course.transpool.graph.component.station.StationNode;
import api.components.map.course.transpool.graph.layout.MapGridLayout;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import data.jaxb.MapDescriptor;
import data.transpool.map.BasicMapData;
import exception.data.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class MapGraphModel extends BasicMapData {

    private Model mapGraphModel;

    public MapGraphModel(MapDescriptor JAXBMap) throws TransPoolDataException {
        super(JAXBMap);
    }

    public void createMapModel(Graph graph) {
        mapGraphModel = graph.getModel();
        graph.beginUpdate();
        //Creating the all the stations
        StationManager sm = createStations(mapGraphModel);

        //Creating all the edges.
        createEdges(sm);

        graph.endUpdate();

        graph.layout(new MapGridLayout(sm));
    }

    private StationManager createStations(Model model) {
        StationManager sm = new StationManager(StationNode::new);

        allStops.forEach((string, stop) -> {
            StationNode station = sm.getOrCreate(stop.getX(), stop.getY());
            station.setName(string);
            station.setDetailsSupplier(() -> {
                List<String> trips = new ArrayList<>();
                return new StationDetailsDTO(trips);
            });
            model.addCell(station);
        });

        return sm;
    }

    private CoordinatesManager createCoordinates() {
        CoordinatesManager cm = new CoordinatesManager(CoordinateNode::new);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                mapGraphModel.addCell(cm.getOrCreate(i+1, j+1));
            }
        }

        return cm;
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
}
