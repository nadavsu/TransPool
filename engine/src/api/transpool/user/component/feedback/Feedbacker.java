package api.transpool.user.component.feedback;

import api.transpool.user.account.User;

import java.util.Collection;

/**
 * An interface of someone who can leave feedbacks. They usually contain a list of Feedbackables.
 */
public interface Feedbacker extends User {
    void leaveFeedback(Feedbackable feedbackee, Feedback feedback);
    Collection<Feedbackable> getAllFeedbackables();
}
