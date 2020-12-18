package api.transpool.user.dto;

import api.transpool.trip.request.component.MatchedTripRequestDTO;
import api.transpool.trip.request.component.TripRequestDTO;
import api.transpool.user.account.TransPoolRider;
import constants.Constants;

import java.util.List;
import java.util.Set;

public class TransPoolRiderDTO {

    private List<TripRequestDTO> tripRequests;
    private List<MatchedTripRequestDTO> matchedTripRequests;
    private Set<String> feedbackables;

    private String userType;

    public TransPoolRiderDTO(TransPoolRider rider) {
        this.tripRequests = rider.getTripRequestsDetails();
        this.matchedTripRequests = rider.getMatchedTripRequestDetails();
        this.feedbackables = rider.getAllFeedbackablesUsernames();
        this.userType = Constants.RIDER;
    }

    public List<TripRequestDTO> getTripRequests() {
        return tripRequests;
    }

    public List<MatchedTripRequestDTO> getMatchedTripRequests() {
        return matchedTripRequests;
    }

    public Set<String> getFeedbackables() {
        return feedbackables;
    }

    public String getUserType() {
        return userType;
    }
}
