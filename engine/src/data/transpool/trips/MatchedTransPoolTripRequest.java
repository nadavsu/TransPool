package data.transpool.trips;

import data.transpool.map.TransPoolPath;
import data.transpool.user.TransPoolDriver;
import data.transpool.user.TransPoolRider;
import exceptions.StopNotFoundException;

import java.util.List;

public class MatchedTransPoolTripRequest {
    private int requestID;
    private String transpoolRiderName;
    private String source;
    private String destination;

    private int transpoolTripID;
    private String transpoolDriverName;
    private int tripPrice;
    private double personalFuelConsumption;
    private Time expectedTimeOfArrival;

    public MatchedTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest, PossibleMatch matchedTrip) throws StopNotFoundException {
        this.requestID = transpoolTripRequest.getID();
        this.transpoolRiderName = transpoolTripRequest.getTranspoolRider().getUsername();
        this.source = transpoolTripRequest.getSource();
        this.destination = transpoolTripRequest.getDestination();
        this.tripPrice = matchedTrip.getTripPrice();
        this.expectedTimeOfArrival = matchedTrip.getEstimatedTimeOfArrival();
        this.personalFuelConsumption = matchedTrip.getAverageFuelConsumption();

        this.transpoolTripID = matchedTrip.getTranspoolTripID();
        this.transpoolDriverName = matchedTrip.getTranspoolDriverName();
    }

    public int getRequestID() {
        return requestID;
    }

    public String getTranspoolRiderName() {
        return transpoolRiderName;
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

    public String getTranspoolDriverName() {
        return transpoolDriverName;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public double getPersonalFuelConsumption() {
        return personalFuelConsumption;
    }

    public Time getExpectedTimeOfArrival() {
        return expectedTimeOfArrival;
    }

    public String toString() {
        String matchedTripString = "";
        matchedTripString += "------Matched Trip------\n";
        matchedTripString += "Request ID: " + requestID + "\n";
        matchedTripString += "Name of rider: " + transpoolRiderName + "\n";
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
