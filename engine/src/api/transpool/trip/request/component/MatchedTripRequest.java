package api.transpool.trip.request.component;

import api.transpool.time.component.TimeDay;
import api.transpool.trip.offer.component.TripOfferPartOccurrence;
import api.transpool.trip.matching.component.PossibleRoute;
import api.transpool.user.account.TransPoolDriver;
import exception.parser.RideFullException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchedTripRequest extends BasicTripRequestData {


    private List<TripOfferPartOccurrence> route;
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
        this.expectedTimeOfArrival = possibleRoute.getArrivalTime();
        this.timeOfDeparture = possibleRoute.getDepartureTime();
        this.personalFuelConsumption = possibleRoute.getAverageFuelConsumption();
        this.tripOfferIDs = new HashSet<>();
        this.transpoolDrivers = new HashSet<>();

        for (TripOfferPartOccurrence tripOfferPart : possibleRoute.getRoute()) {
            tripOfferIDs.add(tripOfferPart.getID());
            transpoolDrivers.add(tripOfferPart.getTransPoolDriver());

            tripOfferPart.addRider(transpoolRider);
            tripOfferPart.updateFather(transpoolRider);
        }
    }

    public MatchedTripRequestDTO getDetails() {
        return new MatchedTripRequestDTO(this);
    }

    public boolean isTimeOfArrival() {
        return isArrival;
    }

    public List<TripOfferPartOccurrence> getRoute() {
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
