package api.transpool.user.dto;

import api.transpool.trip.offer.component.TripOfferDTO;
import api.transpool.trip.request.component.MatchedTripRequestPart;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.component.feedback.FeedbacksDTO;
import constants.Constants;

import java.util.List;

public class TransPoolDriverDTO {
    
    private FeedbacksDTO feedbacks;
    private List<TripOfferDTO> tripOffers;
    private List<MatchedTripRequestPart> riders;

    private int feedbacksVersion;
    private int ridersVersion;

    public TransPoolDriverDTO(TransPoolDriver driver) {
        this.tripOffers = driver.getTripOffersDetails();
        this.riders = driver.getRiders();

        this.feedbacksVersion = driver.getFeedbacksVersion();
        this.ridersVersion = driver.getRidersVersion();

    }

    public FeedbacksDTO getFeedbacks() {
        return feedbacks;
    }

    public List<TripOfferDTO> getTripOffers() {
        return tripOffers;
    }

    public List<MatchedTripRequestPart> getRiders() {
        return riders;
    }

    public int getFeedbacksVersion() {
        return feedbacksVersion;
    }

    public int getRidersVersion() {
        return ridersVersion;
    }

}
