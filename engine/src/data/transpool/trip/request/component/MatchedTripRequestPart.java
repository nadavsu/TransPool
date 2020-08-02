package data.transpool.trip.request.component;

import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.offer.component.TripOfferPartOccurrence;
import data.transpool.user.account.TransPoolRider;

/**
 * Used for displaying who's ridin with who in which SubTripOffer.
 */
public class MatchedTripRequestPart {

    private String sourceStopName;
    private String destinationStopName;
    private TimeDay departureTime;
    private TimeDay arrivalTime;
    private String riderUsername;

    public MatchedTripRequestPart(TransPoolRider rider, TripOfferPartOccurrence tripOfferPartOccurrence) {
        this.riderUsername = rider.getUsername();
        this.sourceStopName = tripOfferPartOccurrence.getSourceStop().getName();
        this.departureTime = tripOfferPartOccurrence.getDepartureTime();
        this.destinationStopName = tripOfferPartOccurrence.getDestinationStop().getName();
        this.arrivalTime = tripOfferPartOccurrence.getArrivalTime();
    }

    public String getSourceStopName() {
        return sourceStopName;
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public String getDestinationStopName() {
        return destinationStopName;
    }

    public TimeDay getArrivalTime() {
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
