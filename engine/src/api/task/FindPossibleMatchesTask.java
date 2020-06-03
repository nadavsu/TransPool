package api.task;

import data.transpool.TransPoolData;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

public class FindPossibleMatchesTask extends Task<ObservableList<PossibleMatch>> {

    TransPoolData data;
    TransPoolTripRequest tripRequestToMatch;
    int maximumMatches;

    public FindPossibleMatchesTask(TransPoolData data, TransPoolTripRequest tripRequestToMatch, int maximumMatches) {
        this.data = data;
        this.tripRequestToMatch = tripRequestToMatch;
        this.maximumMatches = maximumMatches;
    }

    @Override
    protected ObservableList<PossibleMatch> call() throws Exception {
        ObservableList<PossibleMatch> possibleMatches = FXCollections.observableArrayList();

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

        return possibleMatches;
    }
}
