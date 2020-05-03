package data.transpool.trips;

import data.transpool.map.TransPoolPath;
import data.transpool.user.TransPoolRider;
import exceptions.StopNotFoundException;

import java.util.List;

public class MatchedTransPoolTripRequest {

    private TransPoolTripRequest transpoolTripRequest;
    private TransPoolTrip matchedTrip;

    private double personalFuelConsumption;
    private int tripPrice;
    private Time expectedTimeOfArrival;

    public MatchedTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) throws StopNotFoundException {
        this.transpoolTripRequest = transpoolTripRequest;
        this.transpoolTripRequest.setMatched(true);
        this.matchedTrip = matchedTrip;
        this.matchedTrip.addPassenger();

        calculateTripPrice();
        calculatePersonalFuelConsumption();
        calculateExpectedTimeOfArrival();
    }

    private void calculateTripPrice() throws StopNotFoundException {
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(), transpoolTripRequest.getDestination());
        tripPrice = subRoutePath
                .stream()
                .mapToInt(p -> p.getLength() * matchedTrip.getPPK())
                .sum();
    }

    private void calculatePersonalFuelConsumption() throws StopNotFoundException {
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());
        personalFuelConsumption = subRoutePath
                .stream()
                .mapToDouble(TransPoolPath::getFuelConsumption)
                .sum();
    }

    private void calculateExpectedTimeOfArrival() throws StopNotFoundException {
        expectedTimeOfArrival = transpoolTripRequest.getTimeOfDeparture();
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());
        int minutesToAdd = subRoutePath
                .stream()
                .mapToInt(TransPoolPath::getPathTime)
                .sum();
        expectedTimeOfArrival.addMinutes(minutesToAdd);
    }

    public TransPoolTripRequest getTranspoolTripRequest() {
        return transpoolTripRequest;
    }

    public TransPoolTrip getMatchedTrip() {
        return matchedTrip;
    }

    public Time getExpectedTimeOfArrival() {
        return expectedTimeOfArrival;
    }

    public double getPersonalFuelConsumption() {
        return personalFuelConsumption;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public int getID() {
        return transpoolTripRequest.getID();
    }

    public TransPoolRider getRider() {
        return transpoolTripRequest.getTranspoolRider();
    }

    public String getSource() {
        return transpoolTripRequest.getSource();
    }

    public String getDestination() {
        return transpoolTripRequest.getDestination();
    }

    public Time getTimeOfDeparture() {
        return transpoolTripRequest.getTimeOfDeparture();
    }

    public boolean isContinuous() {
        return transpoolTripRequest.isContinuous();
    }

    public String toString() {
        String matchedTripString = "";
        matchedTripString += "------Matched Trip------\n";
        matchedTripString += transpoolTripRequest.toString();
        matchedTripString += "Matched trip ID: " + matchedTrip.getID();
        matchedTripString += "Name of driver: " + matchedTrip.getOwner();
        matchedTripString += "Price of trip: " + tripPrice;
        matchedTripString += "Expected time of arrival: " + expectedTimeOfArrival;
        matchedTripString += "Personal fuel consumption: " + personalFuelConsumption;

        return matchedTripString;
    }
}
