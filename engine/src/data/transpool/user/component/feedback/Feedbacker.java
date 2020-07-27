package data.transpool.user.component.feedback;

import exception.data.FeedbackableNotFoundException;

import java.util.Collection;

/**
 * An interface of someone who can leave feedbacks. They usually contain a list of Feedbackables.
 */
public interface Feedbacker {
    String getFeedbackerName();
    int getFeedbackerID();
    void leaveFeedback(Feedbackable feedbackee, Feedback feedback);
    Collection<Feedbackable> getAllFeedbackables();
}
