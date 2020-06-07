package data.transpool.trip.offer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Feedback {
    private StringProperty feedbackerName;
    private IntegerProperty rating;
    private StringProperty comment;

    public Feedback(int rating, String comment) {
        this.rating = new SimpleIntegerProperty(rating);
        this.comment = new SimpleStringProperty(comment);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getFeedbackerName() {
        return feedbackerName.get();
    }

    public StringProperty feedbackerNameProperty() {
        return feedbackerName;
    }

    public void setFeedbackerName(String feedbackerName) {
        this.feedbackerName.set(feedbackerName);
    }

    @Override
    public String toString() {
        return "a";
    }
}
