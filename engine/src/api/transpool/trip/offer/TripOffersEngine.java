package api.transpool.trip.offer;

import api.transpool.time.component.Updatable;
import api.transpool.trip.matching.component.TripOffersGraph;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.offer.component.TripOfferDTO;
import api.transpool.trip.offer.component.TripOfferPart;

import java.util.List;
import java.util.Map;

/**
 * The engine interface responsible for controlling the trip offers.
 */
public interface TripOffersEngine extends Updatable {
    List<TripOfferDTO> getTripOffersDetails();

    TripOffer getTripOffer(int ID);

    void addTripOffer(TripOffer tripOffer);

    Map<Integer, TripOffer> getAllTripOffers();

    int getNumOfTripOffers();

    TripOfferPart getTripOfferPart(int tripOfferID, int subTripOfferID);

    TripOffersGraph getTripOffersGraph();

    //Live details

    List<TripOffer> getCurrentOffers();

    List<TripOfferPart> getCurrentTripOfferParts();

}
