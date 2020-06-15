package api.components;

import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.collections.ObservableList;

public interface TripOfferEngine {

    TripOffer getTripOffer(int ID);

    void addTripOffer(TripOffer tripOffer);

    ObservableList<TripOffer> getAllTripOffers();

    void updateAfterMatch(PossibleRoute chosenPossibleRoute, MatchedTripRequest matchedTripRequest);
}
