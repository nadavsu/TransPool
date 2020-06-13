package data.transpool.trip.offer.data;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.graph.SubTripOffer;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;


public interface TripOffer extends BasicTripOffer {
    /*ObservableList<Feedback> getFeedbacks();
    int getAverageRating();
    IntegerProperty averageRatingProperty();
    Feedback getFeedback(int index);*/

    ObservableList<BasicTripRequest> getAllMatchedRequestsData();
    void setAllMatchedRequestsData(ObservableList<BasicTripRequest> allMatchedRequestsData);

    List<Path> getUsedPaths();
    Map<Stop, LocalTime> getTimeTable();
    List<SubTripOffer> getRoute();
    ObservableList<String> getRouteAsStopsList();

    void updateAfterMatch(MatchedTripRequest matchedRequest);

    LocalTime getDepartureTimeAtStop(Stop stop);

    SubTripOffer getSubTripOffer(int subTripOfferID);
}
