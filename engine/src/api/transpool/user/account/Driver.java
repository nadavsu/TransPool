package api.transpool.user.account;

import api.transpool.SingleMapEngine;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.offer.component.TripOfferDTO;
import api.transpool.trip.request.component.MatchedTripRequestPart;

import java.util.Collection;

public interface Driver {
    Collection<TripOffer> getTripOffers();
    Collection<TripOfferDTO> getTripOffersDetails();
    Collection<TripOfferDTO> getTripOffersDetails(SingleMapEngine map);
    Collection<MatchedTripRequestPart> getRiders();
    void addTripOffer(TripOffer offer);

    void addRider(MatchedTripRequestPart matchedPart);
}
