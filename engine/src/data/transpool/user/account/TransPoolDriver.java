package data.transpool.user.account;

import data.transpool.TransPoolMap;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.user.component.feedback.Feedback;
import data.transpool.user.component.feedback.Feedbackable;

import java.util.ArrayList;
import java.util.List;

public class TransPoolDriver extends TransPoolUserAccount implements Feedbackable, Driver {

    private static int IDGenerator = 30000;
    private List<Feedback> feedbacks;
    private List<TripOffer> tripOffers;
    private double averageRating;
    private double totalRating;

    public TransPoolDriver(String username) {
        super(username);
        this.setID(IDGenerator++);
        this.feedbacks = new ArrayList<>();
        this.averageRating = 0;
        this.totalRating = 0;
    }

    public TransPoolDriver(TransPoolDriver other) {
        super(other.username);
        this.setID(other.getID());
        this.feedbacks = new ArrayList<>(other.feedbacks);
        this.tripOffers = new ArrayList<>(other.tripOffers);
        this.averageRating = other.averageRating;
        this.totalRating = other.totalRating;
    }

    @Override
    public List<TripOffer> getTripOffers() {
        return tripOffers;
    }

    @Override
    public void addTripOffer(TransPoolMap map, TripOffer offer) {
        map.addTripOffer(offer);
        tripOffers.add(offer);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbacks;
    }

    @Override
    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        this.totalRating += feedback.getRating();
        this.averageRating = (totalRating / feedbacks.size());
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

