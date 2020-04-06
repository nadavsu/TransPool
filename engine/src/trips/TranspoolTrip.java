package trips;

import map.Stop;
import user.*;

import java.util.List;

public class TranspoolTrip {
    private static int IDGenerator = 20000;
    private int ID;
    private Driver driver;
    private Route route;
    private int PPK;
    private Schedule schedule;
    private Time timeOfArrival;
    private int riderCapacity;
    private int[] matchedIDs;                   //This is the ID of the requestedTrips that got matched to this ride.
    private List<Stop> rideStops;               //TODO: This is not defined right.
    private int expectedFuelConsumption;

    public int getID() {
        return ID;
    }

    public Driver getDriver() {
        return driver;
    }

    public Route getRoute() {
        return route;
    }

    public int getPPK() {
        return PPK;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Time getTimeOfArrival() {
        return timeOfArrival;
    }

    public int getRiderCapacity() {
        return riderCapacity;
    }

    public List<Stop> getRideStops() {
        return rideStops;
    }

    public int getExpectedFuelConsumption() {
        return expectedFuelConsumption;
    }
}
