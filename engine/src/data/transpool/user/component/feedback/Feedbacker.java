package data.transpool.user.component.feedback;

import data.transpool.user.account.User;
import exception.data.FeedbackableNotFoundException;

import java.util.Collection;

/**
 * An interface of someone who can leave feedbacks. They usually contain a list of Feedbackables.
 */
public interface Feedbacker extends User {
    void leaveFeedback(Feedbackable feedbackee, Feedback feedback);
    Collection<Feedbackable> getAllFeedbackables();
}
