package data.transpool.trip.offer.data;

import data.transpool.map.component.Stop;
import data.transpool.trip.Route;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.time.LocalTime;


public interface TripOffer extends BasicTripOffer {
    /*ObservableList<Feedback> getFeedbacks();
    int getAverageRating();
    IntegerProperty averageRatingProperty();
    Feedback getFeedback(int index);*/


    Route getRoute();
    void setRoute(Route route);
    ObjectProperty<Route> routeProperty();

    int getPassengerCapacity();
    void setPassengerCapacity(int passengerCaparcity);
    IntegerProperty passengerCapacityProperty();

    ObservableList<BasicTripRequest> getAllMatchedRequestsData();
    void setAllMatchedRequestsData(ObservableList<BasicTripRequest> allMatchedRequestsData);

    boolean containsSubRoute(String source, String destination);
    void updateAfterMatch(MatchedTripRequest matchedRequest);

    LocalTime getDepartureTimeAtStop(Stop stopName);
}
