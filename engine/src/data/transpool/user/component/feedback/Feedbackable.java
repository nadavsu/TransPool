package data.transpool.user.component.feedback;

import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * An interface of an object which can be feedbacked.
 */
public interface Feedbackable {
    void addFeedback(Feedback feedback);
    Feedback getFeedback(int riderID);
    List<Feedback> getAllFeedbacks();
    double getAverageRating();
    double averageRatingProperty();
}
