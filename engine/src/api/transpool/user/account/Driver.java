package api.transpool.user.account;

import api.transpool.SingleMapEngine;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.offer.component.TripOfferDTO;

import java.util.Collection;

public interface Driver {
    Collection<TripOffer> getTripOffers();
    Collection<TripOfferDTO> getTripOffersDetails();
    Collection<TripOfferDTO> getTripOffersDetailsFromMap(SingleMapEngine map);
    void addTripOffer(TripOffer offer);
}
