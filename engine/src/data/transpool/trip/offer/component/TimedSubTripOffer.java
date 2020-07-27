package data.transpool.trip.offer.component;

import data.transpool.time.component.TimeDay;
import data.transpool.trip.request.component.BasicTripRequest;
import exception.data.RideFullException;

/**
 * A sub trip offer with a timestamp. THis class is used for the matching, and searching for relevant routes.
 */

public class TimedSubTripOffer {
    private SubTripOffer subTripOffer;
    private TimeDay departureTime;
    private TimeDay arrivalTime;

    public TimedSubTripOffer(TimeDay departureTime, TimeDay arrivalTime, SubTripOffer subTripOffer) {
        this.subTripOffer = subTripOffer;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public TimedSubTripOffer(TimedSubTripOffer other) {
        this.subTripOffer = other.subTripOffer;
        this.departureTime = other.departureTime;
        this.arrivalTime = other.arrivalTime;
    }

    public void updateFather(int day, BasicTripRequest matchedRequest) throws RideFullException {
        subTripOffer.addRiderOnDay(day, matchedRequest);
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

    public SubTripOffer getSubTripOffer() {
        return subTripOffer;
    }

    @Override
    public String toString() {
        return "Depart from " + subTripOffer.getSourceStop() +
                " with "  +  subTripOffer.getTransPoolDriver() +
                " at " + departureTime +
                " and arrive on " + arrivalTime +
                " at " + subTripOffer.getDestinationStop();
    }
}
