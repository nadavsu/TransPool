package data.transpool.map;

import data.jaxb.MapDescriptor;
import exception.data.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class MapGraphData extends BasicMapData implements MapGraph {
    private ArrayList<List<Stop>> graph;

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
    public ArrayList<List<Stop>> getGraph() {
        return graph;
    }

    @Override
    public ArrayList<List<Stop>> getAllPossibleRoutes(Stop source, Stop destination) {
        boolean[] beingVisited = new boolean[allStops.size()];
        ArrayList<Stop> currentPath = new ArrayList<>();
        ArrayList<List<Stop>> successfulPaths = new ArrayList<>();

        currentPath.add(source);
        depthFirstTraversal(source, destination, beingVisited, currentPath, successfulPaths);

        //todo: maybe you should return ArrayList<List<Path>> instead. If so, convert here.
        return successfulPaths;
    }

    private void depthFirstTraversal(Stop currentStop, Stop destination, boolean[] beingVisited, List<Stop> currentPath, List<List<Stop>> successfulPaths) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            successfulPaths.add(new ArrayList<>(currentPath));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (Stop i : graph.get(currentStop.getID())) {
            if (i != null && !beingVisited[i.getID()]) {
                currentPath.add(i);
                depthFirstTraversal(i, destination, beingVisited, currentPath, successfulPaths);
                currentPath.remove(i);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }


}

