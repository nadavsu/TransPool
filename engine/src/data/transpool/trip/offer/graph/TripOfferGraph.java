package data.transpool.trip.offer.graph;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.offer.data.TripOffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Trip offer weighted graph
 * Vertex - Stop
 * Weight - SubOffer
 */

public class TripOfferGraph {
    private List<List<SubTripOffer>> adjointList;

    public TripOfferGraph() {
        adjointList = new ArrayList<>();
    }

    public TripOfferGraph(int numOfStops, List<TripOffer> allTripOffers) {
        adjointList = new ArrayList<>();

        for (int i = 0; i < numOfStops; i ++) {
            adjointList.add(new ArrayList<>());
        }

        allTripOffers.forEach(this::add);
    }

    public void add(TripOffer tripOffer) {
        tripOffer
                .getRoute()
                .getUsedPaths()
                .forEach(path -> {
                    newConnection(path, tripOffer);
                });
    }

    private void newConnection(Path path, TripOffer tripOffer) {
        int sourceID = path
                .getSourceStop()
                .getID();
        adjointList
                .get(sourceID)
                .add(new SubTripOffer(path, tripOffer));
    }

    /*  TODO: add isArrival and make it possible to get all possible routes using arrival time, meaning you'll have to
        TODO: all the ways in reverse?
     */
    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination) {
        boolean[] beingVisited = new boolean[adjointList.size()];

        PossibleRoute currentRoute = new PossibleRoute();
        PossibleRoutesList possibleRoutes = new PossibleRoutesList();

        depthFirstTraversal(source, destination, beingVisited, currentRoute, possibleRoutes);

        return possibleRoutes;
    }

    private void depthFirstTraversal(Stop currentStop, Stop destination, boolean[] beingVisited,
                                     PossibleRoute currentRoute,
                                     PossibleRoutesList possibleRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            possibleRoutes.add(new PossibleRoute(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (SubTripOffer offer : adjointList.get(currentStop.getID())) {
            if (offer != null && !beingVisited[offer.getDestinationStop().getID()]) {
                currentRoute.add(offer);
                depthFirstTraversal(offer.getDestinationStop(), destination, beingVisited, currentRoute, possibleRoutes);
                currentRoute.remove(offer);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }
}
