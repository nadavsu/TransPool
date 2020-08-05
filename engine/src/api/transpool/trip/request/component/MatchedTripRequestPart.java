package api.transpool.trip.request.component;

import api.transpool.time.component.TimeDay;
import api.transpool.trip.offer.component.TripOfferPartOccurrence;
import api.transpool.user.account.TransPoolRider;

/**
 * Used for displaying who's ridin with who in which SubTripOffer.
 */
public class MatchedTripRequestPart {

    private int tripID;
    private int tripPrice;
    private String sourceStopName;
    private String destinationStopName;
    private String departureTime;
    private String arrivalTime;
    private String riderUsername;

    public MatchedTripRequestPart(TransPoolRider rider, TripOfferPartOccurrence tripOfferPartOccurrence) {
        this.riderUsername = rider.getUsername();
        this.sourceStopName = tripOfferPartOccurrence.getSourceStop().getName();
        this.departureTime = tripOfferPartOccurrence.getDepartureTime().toString();
        this.destinationStopName = tripOfferPartOccurrence.getDestinationStop().getName();
        this.arrivalTime = tripOfferPartOccurrence.getArrivalTime().toString();
        this.tripID = tripOfferPartOccurrence.getID();
        this.tripPrice = tripOfferPartOccurrence.getPrice();
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public int getTripID() {
        return tripID;
    }

    public String getSourceStopName() {
        return sourceStopName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDestinationStopName() {
        return destinationStopName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getRiderUsername() {
        return riderUsername;
    }

    @Override
    public String toString() {
        return riderUsername + " gets on at " + sourceStopName +
                " on " + departureTime +
                " and gets off at " + destinationStopName +
                " on " + arrivalTime;
    }
}
