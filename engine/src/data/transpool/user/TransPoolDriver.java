package data.transpool.user;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TransPoolDriver extends TransPoolUserAccount implements Feedbackable {

    private static int IDGenerator = 30000;
    private ObservableList<Feedback> feedbacks;
    private IntegerProperty averageRating;

    public TransPoolDriver(String username) {
        super(username);
        this.setID(IDGenerator++);
        this.feedbacks = FXCollections.observableArrayList();
        this.averageRating = new SimpleIntegerProperty(0);
    }

    public TransPoolDriver(TransPoolDriver other) {
        super(other.username.get());
        this.setID(other.getID());
        this.feedbacks = FXCollections.observableArrayList(other.feedbacks);
        this.averageRating = new SimpleIntegerProperty(other.getAverageRating());
    }

    @Override
    public ObservableList<Feedback> getAllFeedbacks() {
        return feedbacks;
    }

    @Override
    public int getAverageRating() {
        return averageRating.get();
    }

    @Override
    public IntegerProperty averageRatingProperty() {
        return averageRating;
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);

    }

    @Override
    public Feedback getFeedback(int riderID) {
        return feedbacks
                .stream()
                .filter(feedback -> feedback.getFeedbackerID() == riderID)
                .findFirst()
                .orElse(null);
    }
}

