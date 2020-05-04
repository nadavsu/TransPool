package api;

import data.transpool.TransPoolData;
import data.transpool.trips.MatchedTransPoolTripRequest;
import data.transpool.trips.PossibleMatch;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TransPoolTripRequest;
import exceptions.NoMatchesFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match
 * and the chosen TP trip to match.
 */
public class MatchingEngine extends Engine {
    private TransPoolTripRequest tripRequestToMatch;
    private List<PossibleMatch> possibleMatches;
    private PossibleMatch chosenTransPoolTripToMatch;

    public MatchingEngine() {
        possibleMatches = new ArrayList<>();

    }

    /**
     * Populates the list of all possible matches. Uses the following predicates to filter requests that don't match:
     *      - Time match predicate: Checks if the time of the request and the time of the offered trip match.
     *      - Contains sub-route predicate: Checks if the TransPool trip contains a sub-route of the request's source
     *        and destination.
     *      - Filters all the trips that don't have anymore capacity for passengers.
     * @param tripRequestID - The ID of the trip request to match.
     * @param maximumMatches - The maximum number of matches you want to show.
     * @throws NoMatchesFoundException - If no match was found for the the trip request.
     */
    public void findPossibleMatches(int tripRequestID, int maximumMatches) throws NoMatchesFoundException {
        tripRequestToMatch = TransPoolData.getAllTransPoolTripRequests().getTripRequestByID(tripRequestID);

        Predicate<TransPoolTrip> containsSubRoutePredicate = transPoolTrip ->
                transPoolTrip.containsSubRoute(tripRequestToMatch.getSource(), tripRequestToMatch.getDestination());

        Predicate<TransPoolTrip> timeMatchPredicate = transPoolTrip ->
                transPoolTrip.getSchedule().getTime().equals(tripRequestToMatch.getTimeOfDeparture());

        possibleMatches = TransPoolData
                .getAllTransPoolTrips()
                .getTranspoolTrips()
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
    }

    /**
     * Creates a new match after a possible match was chosen.
     * Updates TransPoolData after a match was made.
     * @param indexOfPossibleMatchesList - The index of the chosen possible match in the possible matches list.
     */
    public void createNewMatch(int indexOfPossibleMatchesList) {
        chosenTransPoolTripToMatch = possibleMatches.get(indexOfPossibleMatchesList);
        MatchedTransPoolTripRequest theMatchedRequest = new MatchedTransPoolTripRequest(tripRequestToMatch, chosenTransPoolTripToMatch);
        TransPoolData.addMatch(theMatchedRequest);

        updateTransPoolDataAfterMatch(theMatchedRequest);
    }

    /**
     * Updates the TransPool trip after the match
     * Deletes the matched trip request.
     * @param theMatchedRequest
     */
    private void updateTransPoolDataAfterMatch(MatchedTransPoolTripRequest theMatchedRequest) {
        chosenTransPoolTripToMatch.getPossibleTransPoolTrip().updateAfterMatch(theMatchedRequest);
        TransPoolData.allTransPoolTripRequests.deleteTripRequest(tripRequestToMatch);
    }

    /**
     * @return the list of all possible matches.
     */
    public List<PossibleMatch> getPossibleMatches() {
        return possibleMatches;
    }
}
