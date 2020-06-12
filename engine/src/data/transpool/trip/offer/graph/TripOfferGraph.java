package data.transpool.trip.offer.graph;

import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.offer.data.TripOffer;

import java.time.LocalTime;
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
                .forEach(this::newConnection);
    }

    private void newConnection(SubTripOffer subTripOffer) {
        int sourceID = subTripOffer.getSourceStop().getID();
        adjointList.get(sourceID).add(subTripOffer);
    }

    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, LocalTime departureTime, int day) {
        boolean[] beingVisited = new boolean[adjointList.size()];

        PossibleRoute currentRoute = new PossibleRoute();
        PossibleRoutesList possibleRoutes = new PossibleRoutesList();

        depthFirstTraversal(source, destination, beingVisited, currentRoute, possibleRoutes);

        return possibleRoutes;
    }

    private void depthFirstTraversal(Stop currentStop, Stop destination,
                                     boolean[] beingVisited, PossibleRoute currentRoute,
                                     PossibleRoutesList possibleRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            possibleRoutes.add(new PossibleRoute(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (SubTripOffer nextOffer : adjointList.get(currentStop.getID())) {
            if (nextOffer != null && !beingVisited[nextOffer.getDestinationStop().getID()]) {
                currentRoute.add(nextOffer);
                if (currentRoute.lastOffer().isBefore(nextOffer) || !nextOffer.getRecurrences().equals("One time")) {
                    depthFirstTraversal(nextOffer.getDestinationStop(), destination, beingVisited,
                            currentRoute, possibleRoutes);
                }
                currentRoute.remove(nextOffer);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }


/*
    private void depthFirstTraversal(Stop currentStop, Stop destination,
                                     boolean[] beingVisited, PossibleRoute currentRoute,
                                     PossibleRoutesList possibleRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            possibleRoutes.add(new PossibleRoute(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (SubTripOffer nextOffer : adjointList.get(currentStop.getID())) {
            if (nextOffer != null && !beingVisited[nextOffer.getDestinationStop().getID()]) {
                currentRoute.add(nextOffer);
                if (currentRoute.lastOffer().isBefore(nextOffer) || !nextOffer.getRecurrences().equals("One time")) {
                    depthFirstTraversal(nextOffer.getDestinationStop(), destination, beingVisited,
                                        currentRoute, possibleRoutes);
                }
                currentRoute.remove(nextOffer);
            }
        }

        beingVisited[currentStop.getID()] = false;
    }
*/

    private boolean isBefore(LocalTime time1, int day1, LocalTime time2, int day2) {
        //isBefore(departureTime, day, nextOffer.getTimeOfDepartureFromSource(), nextOffer.getDay())
        if (day1 < day2) {
            return true;
        } else if (day1 == day2) {
            return time1.isBefore(time2)
                    || time1.equals(time2);
        } else {
            return false;
        }
    }
}
