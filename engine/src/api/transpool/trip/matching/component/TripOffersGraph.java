package api.transpool.trip.matching.component;

import api.transpool.map.component.Stop;
import api.transpool.time.component.TimeDay;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.offer.component.TripOfferPart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Trip offer weighted graph
 * Vertex - Stop
 * Weight - SubTripOffer
 */

public class TripOffersGraph {
    private List<List<TripOfferPart>> adjointList;

    public TripOffersGraph() {
        adjointList = new ArrayList<>();
    }

    public TripOffersGraph(int numOfStops, Collection<TripOffer> allTripOffers) {
        adjointList = new ArrayList<>();
        for (int i = 0; i < numOfStops; i ++) {
            adjointList.add(new ArrayList<>());
        }
        allTripOffers.forEach(this::add);
    }

    public void add(TripOffer tripOffer) {
        tripOffer
                .getRoute()
                .forEach(this::newConnection);
    }

    private void newConnection(TripOfferPart tripOfferPart) {
        int sourceID = tripOfferPart.getSourceStop().getID();
        adjointList.get(sourceID).add(tripOfferPart);
    }

    /**
     * The main function for finding all possible routes from Stop source to Stop destination. Only takes into account
     * the departure time. ***Arrival time is not yet done!***
     * @param source - The source to depart from
     * @param destination - The destination to arrive at
     * @param departureTime - The desired departure time.
     * @return -  A list of possible routes from source to destination, departing AFTER departure time.
     */
    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, TimeDay departureTime) {
        boolean[] beingVisited = new boolean[adjointList.size()];

        PossibleRoute currentRoute = new PossibleRoute();
        PossibleRoutesList possibleRoutes = new PossibleRoutesList();

        depthFirstTraversal(source, destination, departureTime, beingVisited, currentRoute, possibleRoutes);

        return possibleRoutes;
    }


    /**
     * The auxiliary function for finding the possible routes.
     * Uses DFS recursive algorithm. Traverses SubTripOffers, relevant STOs are converted to timedSTOs.
     * "if (currentRoute.add(nextOffer, departureTime))" (line 89) is where the magic happens.
     * @param currentStop - The... current stop.
     * @param destination - The desired destination
     * @param departureTime - The current departure time.
     * @param beingVisited - An array of booleans to check if the stops are already visited.
     * @param currentRoute - The current route built - a stack.
     * @param possibleRoutes - The value which will be returned at the end.
     */
    private void depthFirstTraversal(Stop currentStop, Stop destination, TimeDay departureTime,
                                     boolean[] beingVisited, PossibleRoute currentRoute,
                                     PossibleRoutesList possibleRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            possibleRoutes.add(new PossibleRoute(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (TripOfferPart nextOffer : adjointList.get(currentStop.getID())) {
            if (nextOffer != null && !beingVisited[nextOffer.getDestinationStop().getID()]) {
                if (currentRoute.add(nextOffer, departureTime)) {
                    depthFirstTraversal(nextOffer.getDestinationStop(), destination, currentRoute.getArrivalTime(), beingVisited,
                            currentRoute, possibleRoutes);
                    currentRoute.remove(nextOffer);
                }
            }
        }

        beingVisited[currentStop.getID()] = false;
    }
}
