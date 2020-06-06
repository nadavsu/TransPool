package api.components;

import data.transpool.trip.offer.TripOffer;
import javafx.collections.ObservableList;

public interface TripOfferEngine {

    TripOffer getTripOffer(int ID);

    void addTripOffer(TripOffer tripOffer);

    ObservableList<TripOffer> getAllTripOffers();
}
