package data.transpool.trip.request.component;

import data.transpool.time.component.TimeDay;
import data.transpool.trip.offer.component.TimedSubTripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.user.component.feedback.Feedback;
import data.transpool.user.component.feedback.Feedbackable;
import data.transpool.user.component.feedback.Feedbacker;
import data.transpool.user.account.TransPoolDriver;
import exception.data.RideFullException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchedTripRequest extends BasicTripRequestData {


    private List<TimedSubTripOffer> route;
    private Set<Integer> tripOfferIDs;
    private Set<TransPoolDriver> transpoolDrivers;


    private int tripPrice;
    private double personalFuelConsumption;
    private TimeDay expectedTimeOfArrival;
    private TimeDay timeOfDeparture;
    private boolean isArrival;

    public MatchedTripRequest(TripRequest tripRequestToMatch, PossibleRoute possibleRoute) throws RideFullException {
        super(tripRequestToMatch);
        this.isArrival = tripRequestToMatch.isTimeOfArrival();
        this.route = possibleRoute.getRoute();
        this.tripPrice = possibleRoute.getTotalPrice();
        this.expectedTimeOfArrival = possibleRoute.getTimeOfArrival();
        this.timeOfDeparture = possibleRoute.getTimeOfDeparture();
        this.personalFuelConsumption = possibleRoute.getAverageFuelConsumption();
        this.tripOfferIDs = new HashSet<>();
        this.transpoolDrivers = new HashSet<>();

        possibleRoute.getRoute().forEach(subTripOffer -> {
            tripOfferIDs.add(subTripOffer.getSubTripOffer().getOfferID());
            transpoolDrivers.add(subTripOffer.getSubTripOffer().getTransPoolDriver());
            subTripOffer.getSubTripOffer().getMainOffer().updateAfterMatch(this, subTripOffer);
        });
        updateSubTripOffers();
    }

    private void updateSubTripOffers() throws RideFullException {
        for (TimedSubTripOffer timedOffer : route) {
            timedOffer.updateFather(timedOffer.getDay(), this);
        }
    }

    public boolean isTimeOfArrival() {
        return isArrival;
    }

    public List<TimedSubTripOffer> getRoute() {
        return route;
    }

    public Set<Integer> getTripOfferIDs() {
        return tripOfferIDs;
    }

    public Set<TransPoolDriver> getTransPoolDrivers() {
        return transpoolDrivers;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public double getPersonalFuelConsumption() {
        return personalFuelConsumption;
    }

    public TimeDay getExpectedTimeOfArrival() {
        return expectedTimeOfArrival;
    }

    public TimeDay getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public boolean isArrival() {
        return isArrival;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
