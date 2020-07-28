package data.transpool.old;

import data.transpool.time.component.TimeDay;
import data.transpool.trip.offer.component.TripOfferPart;
import data.transpool.trip.request.component.BasicTripRequest;
import exception.data.RideFullException;

/**
 * A sub trip offer with a timestamp. THis class is used for the matching, and searching for relevant routes.
 */

public class TimedSubTripOffer {
    private TripOfferPart tripOfferPart;
    private TimeDay departureTime;
    private TimeDay arrivalTime;

    public TimedSubTripOffer(TimeDay departureTime, TimeDay arrivalTime, TripOfferPart tripOfferPart) {
        this.tripOfferPart = tripOfferPart;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public TimedSubTripOffer(TimedSubTripOffer other) {
        this.tripOfferPart = other.tripOfferPart;
        this.departureTime = other.departureTime;
        this.arrivalTime = other.arrivalTime;
    }

    public void updateFather(int day, BasicTripRequest matchedRequest) throws RideFullException {
        //tripOfferPart.addRiderOnDay(day, matchedRequest);
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public TimeDay getArrivalTime() {
        return arrivalTime;
    }

    public int getDay() {
        return arrivalTime.getDay();
    }

    public TripOfferPart getTripOfferPart() {
        return tripOfferPart;
    }

    @Override
    public String toString() {
        return "Depart from " + tripOfferPart.getSourceStop() +
                " with "  +  tripOfferPart.getTransPoolDriver() +
                " at " + departureTime +
                " and arrive on " + arrivalTime +
                " at " + tripOfferPart.getDestinationStop();
    }
}
