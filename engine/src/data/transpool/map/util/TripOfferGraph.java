package data.transpool.map.util;

import data.transpool.TransPoolData;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.TripOffer;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TripOfferGraph implements WeightedGraph<Stop, Path, TripOffer> {
    private List<List<Pair<Stop, TripOffer>>> graph;
    private List<Stop> allStops;

    public TripOfferGraph(TransPoolData data) {
        graph = new ArrayList<>();
        allStops = data.getAllStopsAsList();

        for (int i = 0; i < data.getNumberOfStops(); i ++) {
            graph.add(new ArrayList<>());
        }

        initGraph(data.getAllTripOffers());
    }

    private void initGraph(List<TripOffer> allTripOffers) {
        allTripOffers
                .forEach(tripOffer -> {
                    tripOffer
                            .getRoute()
                            .getUsedPaths()
                            .forEach(path -> {
                                newConnection(path, tripOffer);
                    });
        });
    }

    @Override
    public void newConnection(Path path, TripOffer tripOffer) {
        int sourceID = path.getSourceStop().getID();
        Stop destinationStop = path.getDestinationStop();
        graph.get(sourceID).add(new Pair<>(destinationStop, tripOffer));
    }

    @Override
    public ArrayList<List<Pair<Stop, TripOffer>>> getAllPossibleRoutes(Stop source, Stop destination) throws Exception {
        boolean[] beingVisited = new boolean[allStops.size()];

        ArrayList<Pair<Stop, TripOffer>> currentRoute = new ArrayList<>();
        ArrayList<List<Pair<Stop, TripOffer>>> successfulPaths = new ArrayList<>();


        currentRoute.add(new Pair<>(source, null));

        depthFirstTraversal(source, destination, beingVisited, currentRoute, successfulPaths);

        return successfulPaths;
    }

    @Override
    public List<List<Pair<Stop, TripOffer>>> getGraph() {
        return graph;
    }

    private void depthFirstTraversal(Stop currentStop, Stop destination, boolean[] beingVisited,
                                     List<Pair<Stop, TripOffer>> currentRoute,
                                     List<List<Pair<Stop, TripOffer>>> successfulRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            successfulRoutes.add(new ArrayList<>(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (Pair<Stop, TripOffer> i : graph.get(currentStop.getID())) {
            if (i != null && !beingVisited[i.getKey().getID()]) {
                currentRoute.add(i);
                depthFirstTraversal(i.getKey(), destination, beingVisited, currentRoute, successfulRoutes);
                currentRoute.remove(i);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }


}
