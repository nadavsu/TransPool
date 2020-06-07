package data.transpool.user;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

public interface Feedbackable {
    void addFeedback(Feedback feedback);
    Feedback getFeedback(int riderID);
    ObservableList<Feedback> getAllFeedbacks();
    int getAverageRating();
    IntegerProperty averageRatingProperty();
}
