package data.transpool.user.account;

import data.transpool.SingleMapEngine;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.component.TripOfferDTO;

import java.util.Collection;

public interface Driver {
    Collection<TripOffer> getTripOffers();
    Collection<TripOfferDTO> getTripOffersDetails();
    void addTripOffer(SingleMapEngine map, TripOffer offer);
}
