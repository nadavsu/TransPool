package api;

import data.transpool.TransPoolData;
import data.transpool.trips.MatchedTransPoolTripRequest;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TransPoolTripRequest;
import exceptions.NoMatchesFoundException;
import exceptions.StopNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MatchingEngine extends Engine {
    private TransPoolTripRequest tripRequestToMatch;
    private List<TransPoolTrip> possibleMatches;

    public MatchingEngine() {
        possibleMatches = new ArrayList<>();

    }

    public void findPossibleMatches(int tripID, int maximumMatches) throws NoMatchesFoundException {
        tripRequestToMatch = TransPoolData.getAllTransPoolTripRequests().getTripRequestByID(tripID);

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
                .collect(Collectors.toList());

        if (possibleMatches.isEmpty()) {
            throw new NoMatchesFoundException();
        }
    }

    public void createNewMatch(int indexOfPossibleMatchesList) throws StopNotFoundException {
        TransPoolTrip chosenTransPoolTrip = possibleMatches.get(indexOfPossibleMatchesList);
        TransPoolData.addMatch(new MatchedTransPoolTripRequest(tripRequestToMatch, chosenTransPoolTrip));
    }

    public List<TransPoolTrip> getPossibleMatches() {
        return possibleMatches;
    }

    public TransPoolTrip getPossibleMatch(int index) {
        return possibleMatches.get(index);
    }
}
