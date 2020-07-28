package data.transpool.trip.offer.component;

import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.request.component.MatchedTripRequestPart;

import java.util.ArrayList;
import java.util.List;

public class TripOfferDTO {
    private String driverName;
    private TimeDay departureTime;
    private Stop sourceStop;
    private Stop destinationStop;
    private List<MatchedTripRequestPart> matchedRequestsDetails;
    private int PPK;
    private double fuelConsumption;

    public TripOfferDTO(TripOffer tripOffer) {
        this.driverName = tripOffer.getTransPoolDriver().getUsername();
        this.departureTime = tripOffer.getDepartureTime();
        this.sourceStop = tripOffer.getSourceStop();
        this.destinationStop = tripOffer.getDestinationStop();
        this.matchedRequestsDetails = new ArrayList<>(tripOffer.getMatchedRequestsDetails());
        this.PPK = tripOffer.getPPK();
        this.fuelConsumption = tripOffer.getAverageFuelConsumption();
    }

    public String getDriverName() {
        return driverName;
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }

    public List<MatchedTripRequestPart> getMatchedRequestsDetails() {
        return matchedRequestsDetails;
    }

    public int getPPK() {
        return PPK;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }
}
