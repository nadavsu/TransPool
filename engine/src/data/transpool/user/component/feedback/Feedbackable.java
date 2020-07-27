package data.transpool.user.component.feedback;


import java.util.Collection;

/**
 * An interface of an object which can be feedbacked.
 */
public interface Feedbackable {
    void addFeedback(Feedback feedback);
    Feedback getFeedback(int riderID);
    Collection<Feedback> getAllFeedbacks();
    double getAverageRating();
}
