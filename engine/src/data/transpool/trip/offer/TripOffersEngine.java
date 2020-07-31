package data.transpool.trip.offer;

import data.transpool.Updatable;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.component.TripOfferDTO;
import data.transpool.trip.offer.component.TripOfferPart;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.request.component.TripRequest;

import java.util.Collection;
import java.util.List;

/**
 * The engine interface responsible for controlling the trip offers.
 */
public interface TripOffersEngine extends Updatable {
    List<TripOfferDTO> getTripOffersDetails();

    TripOffer getTripOffer(int ID);

    void addTripOffer(TripOffer tripOffer);

    List<TripOffer> getAllTripOffers();

    int getNumOfTripOffers();

    TripOfferPart getSubTripOffer(int tripOfferID, int subTripOfferID);

    //TODO: this should go in Matching Engine or something
    PossibleRoutesList getAllPossibleRoutes(TripRequest tripRequest, int maximumMatches);
    //Live details

    List<TripOffer> getCurrentOffers();

    List<TripOfferPart> getCurrentTripOfferParts();


}
