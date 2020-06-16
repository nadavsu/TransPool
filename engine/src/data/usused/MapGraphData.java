package data.usused;

import data.jaxb.MapDescriptor;
import data.transpool.map.BasicMapData;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import exception.data.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class MapGraphData extends BasicMapData implements MapGraphUnused {
    private List<List<Stop>> graph;

    public MapGraphData(MapDescriptor JAXBMap) throws TransPoolDataException {
        super(JAXBMap);
        graph = new ArrayList<>();
        for (int i = 0; i < allStops.size(); i ++) {
            graph.add(new ArrayList<>());
        }
        allPaths.forEach(this::newConnection);
    }

    @Override
    public void newConnection(Path path) {
        int sourceStopIndex = path.getSourceStop().getID();
        Stop destinationStop = path.getDestinationStop();
        if (!graph.get(sourceStopIndex).contains(destinationStop)) {
            graph.get(sourceStopIndex).add(destinationStop);
        }
    }

    @Override
    public List<List<Stop>> getGraph() {
        return graph;
    }

    @Override
    public List<List<Stop>> getAllPossibleRoutes(Stop source, Stop destination) throws TransPoolDataException {
        boolean[] beingVisited = new boolean[allStops.size()];
        ArrayList<Stop> currentPath = new ArrayList<>();
        ArrayList<List<Stop>> successfulPaths = new ArrayList<>();

        currentPath.add(source);
        depthFirstTraversal(source, destination, beingVisited, currentPath, successfulPaths);

        return successfulPaths;
    }


    private void depthFirstTraversal(Stop currentStop, Stop destination, boolean[] beingVisited, List<Stop> currentRoute, List<List<Stop>> successfulRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            successfulRoutes.add(new ArrayList<>(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (Stop i : graph.get(currentStop.getID())) {
            if (i != null && !beingVisited[i.getID()]) {
                currentRoute.add(i);
                depthFirstTraversal(i, destination, beingVisited, currentRoute, successfulRoutes);
                currentRoute.remove(i);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }


}

