package data.transpool.trip.request.component;

import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.offer.component.TimedSubTripOffer;
import data.transpool.user.account.TransPoolRider;

/**
 * Used for displaying who's ridin with who in which SubTripOffer.
 */
public class MatchedTripRequestPart {

    private Stop sourceStop;
    private Stop destinationStop;
    private TimeDay departureTime;
    private TimeDay arrivalTime;
    private TransPoolRider rider;

    public MatchedTripRequestPart(TransPoolRider rider, TimedSubTripOffer timedSubTripOffer) {
        this.rider = rider;
        this.sourceStop = timedSubTripOffer.getSubTripOffer().getSourceStop();
        this.departureTime = timedSubTripOffer.getDepartureTime();
        this.destinationStop = timedSubTripOffer.getSubTripOffer().getDestinationStop();
        this.arrivalTime = timedSubTripOffer.getArrivalTime();
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }

    public TimeDay getArrivalTime() {
        return arrivalTime;
    }

    public TransPoolRider getRider() {
        return rider;
    }

    @Override
    public String toString() {
        return rider + " gets on at " + sourceStop +
                " on " + departureTime +
                " and gets off at " + destinationStop +
                " on " + arrivalTime;
    }
}
