package data.transpool.trip.offer;

import data.transpool.time.component.Updatable;
import data.transpool.trip.matching.component.TripOffersGraph;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.component.TripOfferDTO;
import data.transpool.trip.offer.component.TripOfferPart;

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

    TripOfferPart getTripOfferPart(int tripOfferID, int subTripOfferID);

    TripOffersGraph getTripOffersGraph();

    //Live details

    List<TripOffer> getCurrentOffers();

    List<TripOfferPart> getCurrentTripOfferParts();

}
