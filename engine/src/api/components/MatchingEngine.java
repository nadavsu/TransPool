package api.components;

import api.controller.MatchTripController;
import data.transpool.TransPoolData;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match
 * and the chosen TP trip to match.
 */
public class MatchingEngine {
    private TransPoolTripRequest tripRequestToMatch;
    private List<PossibleMatch> possibleMatches;

    private MatchTripController controller;

    public MatchingEngine(MatchTripController controller) {
        possibleMatches = new ArrayList<>();
        this.controller = controller;
    }

    /**
     * Populates the list of all possible matches. Uses the following predicates to filter requests that don't match:
     *      - Time match predicate: Checks if the time of the request and the time of the offered trip match.
     *      - Contains sub-route predicate: Checks if the TransPool trip contains a sub-route of the request's source
     *        and destination.
     *      - Filters all the trips that don't have anymore capacity for passengers.
     * @param tripRequestID - The ID of the trip request to match.
     * @param maximumMatches - The maximum number of matches you want to show.
     * @param data - the data to search for matches.
     * @throws NoMatchesFoundException - If no match was found for the the trip request.
     */
    public List<PossibleMatch> findPossibleMatches(TransPoolData data, int tripRequestID, int maximumMatches) throws NoMatchesFoundException {
        tripRequestToMatch = data.getTripRequestByID(tripRequestID);

        Predicate<TransPoolTrip> containsSubRoutePredicate = transPoolTrip ->
                transPoolTrip.containsSubRoute(tripRequestToMatch.getSource(), tripRequestToMatch.getDestination());

        Predicate<TransPoolTrip> timeMatchPredicate = transPoolTrip ->
                transPoolTrip.getSchedule().getTime().equals(tripRequestToMatch.getTimeOfDeparture());

        possibleMatches = data
                .getAllTransPoolTrips()
                .stream()
                .filter(t -> t.getPassengerCapacity() > 0)
                .filter(containsSubRoutePredicate)
                .filter(timeMatchPredicate)
                .limit(maximumMatches)
                .map(transpoolTrip -> new PossibleMatch(tripRequestToMatch, transpoolTrip))
                .collect(Collectors.toList());

        if (possibleMatches.isEmpty()) {
            throw new NoMatchesFoundException();
        }
        return possibleMatches;
    }

    /**
     * Creates and adds a new match to data after a possible match was chosen.
     * Updates TransPoolData after a match was made.
     * @param chosenPossibleMatchIndex - The index of the chosen possible match in the possible matches list.
     * @param data                     - The data to add to.
     */
    public void addNewMatch(TransPoolData data, int chosenPossibleMatchIndex) {
        PossibleMatch chosenPossibleMatch = possibleMatches.get(chosenPossibleMatchIndex);
        data.addMatch(new MatchedTransPoolTripRequest(tripRequestToMatch, chosenPossibleMatch));
    }


    /**
     * @return the list of all possible matches.
     */
    public List<PossibleMatch> getPossibleMatches() {
        return possibleMatches;
    }
}
