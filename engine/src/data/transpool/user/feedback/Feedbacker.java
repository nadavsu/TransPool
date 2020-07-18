package data.transpool.user.feedback;

import javafx.collections.ObservableList;

/**
 * An interface of someone who can leave feedbacks. They usually contain a list of Feedbackables.
 */
public interface Feedbacker {
    String getFeedbackerName();
    int getFeedbackerID();
    void leaveFeedback(Feedbackable feedbackee, Feedback feedback);
    ObservableList<Feedbackable> getAllFeedbackables();
}
