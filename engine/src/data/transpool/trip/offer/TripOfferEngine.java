package data.transpool.trip.offer;

import data.transpool.Updatable;
import data.transpool.trip.offer.component.SubTripOffer;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.request.component.TripRequest;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * The engine interface responsible for controlling the trip offers.
 */
public interface TripOfferEngine extends Updatable {

    TripOffer getTripOffer(int ID);

    void addTripOffer(TripOffer tripOffer);

    ObservableList<TripOffer> getAllTripOffers();

    SubTripOffer getSubTripOffer(int tripOfferID, int subTripOfferID);

    //TODO: this should go in Matching Engine or something
    PossibleRoutesList getAllPossibleRoutes(TripRequest tripRequest, int maximumMatches);

    //Live details

    ObservableList<TripOffer> getCurrentOffers();

    List<SubTripOffer> getCurrentSubTripOffers();


}
