package data.transpool.trip.offer;

import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.user.Feedback;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;


public interface TripOffer extends BasicTripOffer {
    /*ObservableList<Feedback> getFeedbacks();
    int getAverageRating();
    IntegerProperty averageRatingProperty();
    Feedback getFeedback(int index);*/


    int getPassengerCapacity();
    void setPassengerCapacity(int passengerCaparcity);
    IntegerProperty passengerCapacityProperty();

    ObservableList<BasicTripRequest> getAllMatchedRequestsData();
    void setAllMatchedRequestsData(ObservableList<BasicTripRequest> allMatchedRequestsData);

    boolean containsSubRoute(String source, String destination);
    void updateAfterMatch(MatchedTripRequest matchedRequest);
}
