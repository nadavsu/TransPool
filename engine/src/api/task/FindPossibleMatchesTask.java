package api.task;

import data.transpool.TransPoolData;
import data.transpool.trip.offer.PossibleMatch;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.request.TripRequest;
import data.transpool.trip.request.TripRequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.function.Predicate;

public class FindPossibleMatchesTask extends Task<ObservableList<PossibleMatch>> {

    private TransPoolData data;
    private TripRequest tripRequestToMatch;
    private int maximumMatches;

    public FindPossibleMatchesTask(TransPoolData data, TripRequestData tripRequestToMatch, int maximumMatches) {
        this.data = data;
        this.tripRequestToMatch = tripRequestToMatch;
        this.maximumMatches = maximumMatches;
    }

    @Override
    protected ObservableList<PossibleMatch> call() throws Exception {
        ObservableList<PossibleMatch> possibleMatches = FXCollections.observableArrayList();

        Predicate<TripOffer> containsSubRoutePredicate = tripOffer ->
                tripOffer.containsSubRoute(tripRequestToMatch.getSourceStop(), tripRequestToMatch.getDestinationStop());

        Predicate<TripOffer> timeMatchPredicate = transPoolTrip ->
                transPoolTrip.getScheduling().getDepartureTime().equals(tripRequestToMatch.getRequestTime());

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
                .forEach(possibleMatches::add);

        return possibleMatches;
    }
}
