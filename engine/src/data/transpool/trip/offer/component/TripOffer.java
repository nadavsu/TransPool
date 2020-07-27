package data.transpool.trip.offer.component;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestPart;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface TripOffer extends BasicTripOffer {
    Collection<Path> getUsedPaths();
    Map<Stop, TimeDay> getTimeTable();
    Collection<SubTripOffer> getRoute();
    Collection<MatchedTripRequestPart> getMatchedRequestsDetails();

    Stop getSourceStop();
    Stop getDestinationStop();
    TimeDay getDepartureTime();
    TimeDay getArrivalTime();
    TimeDay getDepartureTimeAtStop(Stop stop);

    SubTripOffer getSubTripOffer(int subTripOfferID);
    SubTripOffer getCurrentSubTripOffer();
    boolean isCurrentlyHappening();
    void updateAfterMatch(MatchedTripRequest matchedRequest, TimedSubTripOffer subTripOffer);


}
