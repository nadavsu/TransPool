package api.transpool.trip.offer.component;

import api.transpool.map.component.Path;
import api.transpool.map.component.Stop;
import api.transpool.time.component.TimeDay;
import api.transpool.trip.request.component.MatchedTripRequestPart;
import api.transpool.user.account.TransPoolRider;

import java.util.Collection;
import java.util.Map;


public interface SingleTripOfferEngine {
    Collection<Path> getUsedPaths();
    Map<Stop, TimeDay> getTimeTable();
    Collection<TripOfferPart> getRoute();
    Collection<MatchedTripRequestPart> getMatchedRequestsDetails();

    TimeDay getTimeAtStop(Stop stop);

    TripOfferPart getTripOfferPart(int subTripOfferID);
    TripOfferPart getOccurringTripOfferPart();
    boolean isCurrentlyHappening();
    void updateAfterMatch(TransPoolRider transPoolRider, TripOfferPartOccurrence subTripOffer);
    TripOfferDTO getDetails();

}
