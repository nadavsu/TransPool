package data.transpool.trip.offer.component;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.component.Recurrence;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestPart;
import data.transpool.user.account.TransPoolRider;

import java.util.Collection;
import java.util.Map;


public interface SingleTripOfferEngine {
    Collection<Path> getUsedPaths();
    Map<Stop, TimeDay> getTimeTable();
    Collection<TripOfferPart> getRoute();
    Collection<MatchedTripRequestPart> getMatchedRequestsDetails();

    TimeDay getTimeAtStop(Stop stop);

    TripOfferPart getSubTripOffer(int subTripOfferID);
    TripOfferPart getCurrentSubTripOffer();
    boolean isCurrentlyHappening();
    void updateAfterMatch(TransPoolRider transPoolRider, TripOfferPartOccurrence subTripOffer);


}
