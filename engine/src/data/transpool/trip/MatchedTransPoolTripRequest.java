package data.transpool.trip;

import data.transpool.user.TransPoolDriver;
import data.transpool.user.TransPoolRider;

import java.time.LocalTime;

public class MatchedTransPoolTripRequest {
    private int requestID;
    private TransPoolRider transpoolRider;
    private String source;
    private String destination;

    private int transpoolTripID;
    private TransPoolDriver transpoolDriverName;
    private int tripPrice;
    private double personalFuelConsumption;
    private LocalTime expectedTimeOfArrival;

    public MatchedTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest, PossibleMatch matchedTrip) {
        this.requestID = transpoolTripRequest.getRequestID();
        this.transpoolRider = transpoolTripRequest.getTranspoolRider();
        this.source = transpoolTripRequest.getSourceStop();
        this.destination = transpoolTripRequest.getDestinationStop();
        this.tripPrice = matchedTrip.getTripPrice();
        this.expectedTimeOfArrival = matchedTrip.getEstimatedTimeOfArrival();
        this.personalFuelConsumption = matchedTrip.getAverageFuelConsumption();

        this.transpoolTripID = matchedTrip.getTranspoolTripID();
        this.transpoolDriverName = matchedTrip.getTranspoolDriverName();
    }

    public int getRequestID() {
        return requestID;
    }

    public TransPoolRider getTranspoolRider() {
        return transpoolRider;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTranspoolTripID() {
        return transpoolTripID;
    }

    public TransPoolDriver getTranspoolDriverName() {
        return transpoolDriverName;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public double getPersonalFuelConsumption() {
        return personalFuelConsumption;
    }

    public LocalTime getExpectedTimeOfArrival() {
        return expectedTimeOfArrival;
    }

    public String toString() {
        String matchedTripString = "";
        matchedTripString += "------Matched Trip------\n";
        matchedTripString += "Request ID: " + requestID + "\n";
        matchedTripString += "Name of rider: " + transpoolRider + "\n";
        matchedTripString += "Source stop: " + source + "\n";
        matchedTripString += "Destination stop: " + destination + "\n";
        matchedTripString += "Matched trip ID: " + transpoolTripID + "\n";
        matchedTripString += "Name of driver: " + transpoolDriverName + "\n";
        matchedTripString += "Price of trip: " + tripPrice + "\n";
        matchedTripString += "Expected time of arrival: " + expectedTimeOfArrival + "\n";
        matchedTripString += "Personal fuel consumption: " + personalFuelConsumption + "\n";

        return matchedTripString;
    }
}
