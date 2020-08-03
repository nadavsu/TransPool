package data.transpool.user.account;

import data.transpool.SingleMapEngine;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestDTO;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.trip.request.component.TripRequestDTO;
import data.transpool.user.component.feedback.Feedback;
import data.transpool.user.component.feedback.Feedbackable;
import data.transpool.user.component.feedback.Feedbacker;

import java.util.*;
import java.util.stream.Collectors;

public class TransPoolRider extends TransPoolUserAccount implements Rider, Feedbacker {

    private static int IDGenerator = 40000;

    private List<TripRequest> tripRequests;
    private List<MatchedTripRequest> matchedTripRequests;

    private Set<Feedbackable> feedbackables;

    public TransPoolRider(String username) {
        super(username);
        this.tripRequests = new ArrayList<>();
        this.matchedTripRequests = new ArrayList<>();
        this.feedbackables = new HashSet<>();

        this.tripRequests = new ArrayList<>();
        this.matchedTripRequests = new ArrayList<>();
        this.feedbackables = new HashSet<>();
        setID(IDGenerator++);
    }

    public TransPoolRider(TransPoolRider other) {
        super(other.getUsername());
        this.tripRequests = new ArrayList<>(other.tripRequests);
        this.matchedTripRequests = new ArrayList<>(other.matchedTripRequests);
        this.feedbackables = new HashSet<>(other.feedbackables);
        setID(other.getID());
    }

    @Override
    public List<TripRequestDTO> getTripRequestsDetails() {
        return tripRequests
                .stream()
                .map(TripRequest::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatchedTripRequestDTO> getMatchedTripRequestDetails() {
        return matchedTripRequests
                .stream()
                .map(MatchedTripRequest::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripRequestDTO> getTripRequestsDetailsFromMap(SingleMapEngine map) {
        return tripRequests
                .stream()
                .filter(request -> request.isBelongToMap(map))
                .map(TripRequest::getDetails)
                .collect(Collectors.toList());
    }

    @Override
    public void addRequest(TripRequest request) {
        tripRequests.add(request);
    }

    @Override
    public void acceptMatch(MatchedTripRequest matchedRequest) {
        tripRequests.remove(getRequest(matchedRequest.getRequestID()));
        matchedTripRequests.add(matchedRequest);
        feedbackables.addAll(matchedRequest.getTransPoolDrivers());

        matchedRequest
                .getRoute()
                .forEach(tripOfferPartOccurrence -> transferCredit(
                        tripOfferPartOccurrence.getPrice(),
                        tripOfferPartOccurrence.getTransPoolDriver(),
                        tripOfferPartOccurrence.getDepartureTime()
                ));
    }

    @Override
    public List<TripRequest> getAllTripRequests() {
        return tripRequests;
    }

    @Override
    public List<MatchedTripRequest> getAllMatchedTripRequests() {
        return matchedTripRequests;
    }

    @Override
    public TripRequest getRequest(int ID) {
        return tripRequests
                .stream()
                .filter(request -> request.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public MatchedTripRequest getMatchedTripRequest(int ID) {
        return matchedTripRequests
                .stream()
                .filter(match -> match.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void leaveFeedback(Feedbackable feedbackee, Feedback feedback) {
        feedbackee.addFeedback(feedback);
        feedbackables.remove(feedbackee);
    }

    @Override
    public Set<Feedbackable> getAllFeedbackables() {
        return feedbackables;
    }

    public Set<String> getAllFeedbackablesUsernames() {
        return feedbackables
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());
    }

}
