package data.transpool.trip.offer.component;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestPart;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;


public interface TripOffer extends BasicTripOffer {
    double getAverageRating();

    ObservableList<MatchedTripRequestPart> getAllMatchedRequestsData();

    List<Path> getUsedPaths();
    Map<Stop, TimeDay> getTimeTable();
    List<SubTripOffer> getRoute();
    ObservableList<String> getRouteAsStopsList();

    void updateAfterMatch(MatchedTripRequest matchedRequest, TimedSubTripOffer subTripOffer);

    TimeDay getDepartureTimeAtStop(Stop stop);

    SubTripOffer getSubTripOffer(int subTripOfferID);

    boolean isCurrentlyHappening();

    SubTripOffer getCurrentSubTripOffer();


}
