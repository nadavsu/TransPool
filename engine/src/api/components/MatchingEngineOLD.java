package api.components;

import data.transpool.TransPoolMap;
import data.transpool.trip.matching.component.PossibleRoute;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import exception.NoResultsFoundException;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match.
 */
public class MatchingEngineOLD {
    private TripRequest tripRequestToMatch;
    private List<PossibleRoute> possibleRoutes;
    private boolean foundMatches;

    public MatchingEngineOLD() {
        possibleRoutes = new ArrayList<>();
        foundMatches = false;
    }


    /**
     * Finds the possible routes for a request.
     * @param data - The data to search the possible routes on
     * @param tripRequestToMatch - The trip request to match
     * @param maximumMatches - maximum matches;
     * @throws NoResultsFoundException - If no results are found, the user is told.
     */
    public void findPossibleMatches(TransPoolMap data, TripRequest tripRequestToMatch, int maximumMatches) throws NoResultsFoundException {
        this.tripRequestToMatch = tripRequestToMatch;
        //possibleRoutes.addAll(data.getAllPossibleRoutes(tripRequestToMatch, maximumMatches));

        if (!possibleRoutes.isEmpty()) {
            foundMatches = true;
        } else {
            throw new NoResultsFoundException();
        }
    }


    /**
     * Adds a new match to the system and updates all relevant data.
     * @param data - data to update
     * @param chosenPossibleRouteIndex - The chosen possible route in the list possibleRoutes.
     * @throws TransPoolDataException - .
     */
    public void addNewMatch(TransPoolMap data, int chosenPossibleRouteIndex) throws TransPoolDataException {
        PossibleRoute chosenPossibleRoute = possibleRoutes.get(chosenPossibleRouteIndex);
        MatchedTripRequest matchedTripRequest = new MatchedTripRequest(tripRequestToMatch, chosenPossibleRoute);
        data.addMatchedRequest(matchedTripRequest);
    }


    /**
     * @return the list of all possible matches.
     */
    public List<PossibleRoute> getPossibleRoutes() {
        return possibleRoutes;
    }

    /**
     * Clears the engine's data.
     */
    public void clearPossibleMatches() {
        possibleRoutes.clear();
        foundMatches = false;
    }

    public boolean isFoundMatches() {
        return foundMatches;
    }

    public ObservableList<String> getPossibleRoutesAsString() {
        return possibleRoutes
                .stream()
                .map(PossibleRoute::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
