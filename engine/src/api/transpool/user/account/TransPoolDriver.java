package api.transpool.user.account;

import api.transpool.SingleMapEngine;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.offer.component.TripOfferDTO;
import api.transpool.user.component.feedback.Feedback;
import api.transpool.user.component.feedback.FeedbacksDTO;
import api.transpool.user.component.feedback.Feedbackable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransPoolDriver extends TransPoolUserAccount implements Feedbackable, Driver {

    private static int IDGenerator = 30000;

    private List<Feedback> feedbacks;
    private List<TripOffer> tripOffers;

    private double averageRating;
    private double totalRating;

    public TransPoolDriver(String username) {
        super(username);
        this.feedbacks = new ArrayList<>();
        this.tripOffers = new ArrayList<>();

        this.setID(IDGenerator++);
        this.feedbacks = new ArrayList<>();
        this.averageRating = 0;
        this.totalRating = 0;
    }

    @Override
    public List<TripOffer> getTripOffers() {
        return tripOffers;
    }

    @Override
    public List<TripOfferDTO> getTripOffersDetails() {
        return tripOffers
                .stream()
                .map(TripOffer::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripOfferDTO> getTripOffersDetailsFromMap(SingleMapEngine map) {
        return tripOffers
                .stream()
                .filter(offer -> offer.isBelongToMap(map))
                .map(TripOffer::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public void addTripOffer(TripOffer offer) {
        tripOffers.add(offer);
    }

    @Override
    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return Collections.unmodifiableList(feedbacks);
    }

    @Override
    public Feedback getFeedback(int riderID) {
        return feedbacks
                .stream()
                .filter(feedback -> feedback.getFeedbackerID() == riderID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        this.totalRating += feedback.getRating();
        this.averageRating = (totalRating / feedbacks.size());
    }

    public FeedbacksDTO getFeedbacksDetails() {
        return new FeedbacksDTO(this);
    }
}

