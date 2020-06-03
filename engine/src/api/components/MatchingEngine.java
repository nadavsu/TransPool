package api.components;

import api.task.FindPossibleMatchesTask;
import com.sun.org.apache.xpath.internal.operations.Bool;
import data.transpool.TransPoolData;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match
 * and the chosen TP trip to match.
 */
public class MatchingEngine {
    private TransPoolTripRequest tripRequestToMatch;
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
    public void findPossibleMatches(TransPoolData data, TransPoolTripRequest tripRequestToMatch, int maximumMatches) throws NoMatchesFoundException {

        Task findPossibleMatchesTask = new FindPossibleMatchesTask(data, tripRequestToMatch, maximumMatches);

        this.tripRequestToMatch = tripRequestToMatch;
        Predicate<TransPoolTrip> containsSubRoutePredicate = transPoolTrip ->
                transPoolTrip.containsSubRoute(tripRequestToMatch.getSourceStop(), tripRequestToMatch.getDestinationStop());


        Predicate<TransPoolTrip> timeMatchPredicate = transPoolTrip ->
                transPoolTrip.getSchedule().getTime().equals(tripRequestToMatch.getRequestTime());

        data
                .getAllTransPoolTrips()
                .stream()
                .filter(t -> t.getPassengerCapacity() > 0)
                .filter(containsSubRoutePredicate)
                .filter(timeMatchPredicate)
                .limit(maximumMatches)
                .map(transpoolTrip -> new PossibleMatch(tripRequestToMatch, transpoolTrip))
                .forEach(match -> possibleMatches.add(match));

        foundMatches.set(!possibleMatches.isEmpty());
        if (possibleMatches.isEmpty()) {
            throw new NoMatchesFoundException();
        }
    }

    /**
     * Creates and adds a new match to data after a possible match was chosen.
     * Updates TransPoolData after a match was made.
     * @param chosenPossibleMatch - The chosen possible match in the possible matches list.
     * @param data                - The data to add to.
     */
    public void addNewMatch(TransPoolData data, PossibleMatch chosenPossibleMatch) {
        data.addMatch(new MatchedTransPoolTripRequest(tripRequestToMatch, chosenPossibleMatch));
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
