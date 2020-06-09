package api.components;

import data.transpool.TransPoolData;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.map.util.TripOfferGraph;
import data.transpool.map.util.WeightedGraph;
import data.transpool.trip.Route;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.offer.PossibleMatch;
import data.transpool.trip.request.TripRequest;
import exception.NoMatchesFoundException;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match
 * and the chosen TP trip to match.
 */
public class MatchingEngine {
    private TripRequest tripRequestToMatch;
    private ObservableList<PossibleMatch> possibleMatches;
    private BooleanProperty foundMatches;

    public MatchingEngine() {
        possibleMatches = FXCollections.observableArrayList();
        foundMatches = new SimpleBooleanProperty(false);
    }

    /**
     * Populates the list of all possible matches. Uses the following predicates to filter requests that don't match:
     *      - Time match predicate: Checks if the time of the request and the time of the offered trip match.
     *      - Contains sub-route predicate: Checks if the TransPool trip contains a sub-route of the request's source
     *        and destination.
     *      - Filters all the trips that don't have anymore capacity for passengers.
     * @param tripRequestToMatch- The ID of the trip request to match.
     * @param maximumMatches - The maximum number of matches you want to show.
     * @param data - the data to search for matches.
     * @throws NoMatchesFoundException - If no match was found for the the trip request.
     * @return true if found at least one match is found.
     */
    public void findPossibleMatches(TransPoolData data, TripRequest tripRequestToMatch, int maximumMatches) throws NoMatchesFoundException, TransPoolDataException {
        this.tripRequestToMatch = tripRequestToMatch;

        WeightedGraph<Stop, Path, TripOffer> tripOfferGraph = new TripOfferGraph(data);
        try {
            tripOfferGraph.getAllPossibleRoutes(tripRequestToMatch.getSourceStop(), tripRequestToMatch.getDestinationStop());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<List<Stop>> allPossibleRoutes
                = data.getAllPossibleRoutes(tripRequestToMatch.getSourceStop(), tripRequestToMatch.getDestinationStop());




        
        /*
        i need to go through the possible routes
        for each route,
        i need to go through the used paths list
        check if there's a trip offer that goes from the source to the destination at the right departure time
        (if we're talking about time of arrival then we go from the end to the beginning)
         */
/*        for (int i = 0; i < allPossibleRoutes.size(); i++) {
            List<Path> currentRoutePathList = allPossibleRoutes.get(i).getUsedPaths();
            List<TripOffer> offersForCurrentRoute = new ArrayList<>();
            LocalTime currentPathDeparture = tripRequestToMatch.getRequestTime();
            for (Path path : currentRoutePathList) {
                offersForCurrentRoute.add(data.getAllTripOffers()
                        .stream()
                        .filter(tripOffer -> !tripOffer.containsSubRoute(path.getSourceName(), path.getDestinationName()))
                        .filter(tripOffer -> !tripOffer.getDepartureTimeAtStop(path.getSourceStop()).equals(currentPathDeparture))
                        .findFirst()
                        .orElse(null));
            }
        }*/


/*        Predicate<TripOffer> containsSubRoutePredicate = tripOffer ->
                tripOffer.containsSubRoute(tripRequestToMatch.getSourceStop(), tripRequestToMatch.getDestinationStop());


        Predicate<TripOffer> timeMatchPredicate = tripOffer ->
                tripOffer.getScheduling().getDepartureTime().equals(tripRequestToMatch.getRequestTime());

        data
                .getAllTripOffers()
                .stream()
                .filter(t -> t.getPassengerCapacity() > 0)
                .filter(containsSubRoutePredicate)
                .filter(timeMatchPredicate)
                .limit(maximumMatches)
                .map(tripOffer -> new PossibleMatch(tripRequestToMatch.getSourceStop(),
                        tripRequestToMatch.getDestinationStop(),
                        tripOffer))
                .forEach(match -> possibleMatches.add(match));

        foundMatches.set(!possibleMatches.isEmpty());
        if (possibleMatches.isEmpty()) {
            throw new NoMatchesFoundException();
        }*/
    }

    /**
     * Creates and adds a new match to data after a possible match was chosen.
     * Updates TransPoolData after a match was made.
     * @param chosenPossibleMatch - The chosen possible match in the possible matches list.
     * @param data                - The data to add to.
     */
    public void addNewMatch(TransPoolData data, PossibleMatch chosenPossibleMatch) {
        data.addMatchedRequest(new MatchedTripRequest(tripRequestToMatch, chosenPossibleMatch));
    }


    /**
     * @return the list of all possible matches.
     */
    public ObservableList<PossibleMatch> getPossibleMatches() {
        return possibleMatches;
    }

    public void clearPossibleMatches() {
        possibleMatches.clear();
        foundMatches.set(false);
    }

    public BooleanProperty foundMatchesProperty() {
        return foundMatches;
    }
}
