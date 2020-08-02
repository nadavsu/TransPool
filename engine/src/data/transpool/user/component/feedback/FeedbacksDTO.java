package data.transpool.user.component.feedback;

import java.util.Collection;

public class FeedbacksDTO {

    private Collection<Feedback> feedbacks;
    private double averageRating;

    public FeedbacksDTO(Feedbackable feedbackable) {
        this.feedbacks = feedbackable.getAllFeedbacks();
        this.averageRating = feedbackable.getAverageRating();
    }

    public Collection<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public double getAverageRating() {
        return averageRating;
    }
}