package data.transpool.user.account;

import data.transpool.TransPoolMap;
import data.transpool.trip.offer.component.TripOffer;

import java.util.Collection;

public interface Driver {
    Collection<TripOffer> getTripOffers();
    void addTripOffer(TransPoolMap map, TripOffer offer);
}
