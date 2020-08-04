package old;

import api.transpool.trip.offer.component.TripOfferPart;
import api.transpool.trip.request.component.BasicTripRequest;
import exception.parser.RideFullException;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which holds the details of the riders on a specific day
 */
public class SubTripOfferDetails {
    private int maxCapacity;
    private List<BasicTripRequest> riders;

    public SubTripOfferDetails(TripOfferPart tripOffer, BasicTripRequest rider) {
        this.maxCapacity = tripOffer.getMaxPassengerCapacity();
        this.riders = new ArrayList<>(maxCapacity);
        this.riders.add(rider);
    }

    public boolean isRideFull() {
        return riders.size() >= maxCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentNumberOfRiders() {
        return maxCapacity - riders.size();
    }

    public List<BasicTripRequest> getRiders() {
        return riders;
    }

    public void addRider(BasicTripRequest matchedRequest) throws RideFullException{
        if (!isRideFull()) {
            riders.add(matchedRequest);
        } else {
            throw new RideFullException();
        }
    }

    @Override
    public String toString() {
        return "Current riders: " + riders.toString();
    }
}
