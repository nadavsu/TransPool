package data.transpool.trip.offer;

import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;


public interface TripOffer extends BasicTripOffer {
    int getDriverRating();
    void setDriverRating(int rating);
    IntegerProperty ratingProperty();


    int getPassengerCapacity();
    void setPassengerCapacity(int passengerCaparcity);
    IntegerProperty passengerCapacityProperty();

    ObservableList<BasicTripRequest> getAllMatchedRequestsData();
    void setAllMatchedRequestsData(ObservableList<BasicTripRequest> allMatchedRequestsData);

    boolean containsSubRoute(String source, String destination);
    void updateAfterMatch(MatchedTripRequest matchedRequest);
}
