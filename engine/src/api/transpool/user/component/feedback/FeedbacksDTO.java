package api.transpool.user.component.feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbacksDTO {

    private List<Feedback> feedbacks;
    private double averageRating;
    private int version;

    public FeedbacksDTO(Feedbackable feedbackable) {
        this.feedbacks = new ArrayList<>(feedbackable.getAllFeedbacks());
        this.averageRating = feedbackable.getAverageRating();
        this.version = feedbacks.size();
    }

    public FeedbacksDTO(List<Feedback> feedbacks, double averageRating, int version) {
        this.feedbacks = feedbacks;
        this.averageRating = averageRating;
        this.version = version;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getVersion() {
        return version;
    }
}