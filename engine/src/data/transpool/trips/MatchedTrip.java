package data.transpool.trips;

import data.transpool.user.Driver;

public class MatchedTrip extends TripRequest {

    private int matchedTransPoolTripID;
    private Driver driver;
    private int tripPrice;
    private Time timeOfArrival;
    private int tripFuelConsumption;

    public MatchedTrip(TripRequest tripRequest) {
        super(tripRequest);
    }

    public int getMatchedTransPoolTripID() {
        return matchedTransPoolTripID;
    }

    public void setMatchedTransPoolTripID(int matchedTransPoolTripID) {
        this.matchedTransPoolTripID = matchedTransPoolTripID;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(int tripPrice) {
        this.tripPrice = tripPrice;
    }

    public Time getTimeOfArrival() {
        return timeOfArrival;
    }

    public void setTimeOfArrival(Time timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public int getTripFuelConsumption() {
        return tripFuelConsumption;
    }

    public void setTripFuelConsumption(int tripFuelConsumption) {
        this.tripFuelConsumption = tripFuelConsumption;
    }
}
