package data.transpool.trips;

import data.generated.TransPool;
import data.transpool.map.Stop;
import data.transpool.user.*;

import java.util.List;

public class TransPoolTrip {
    private static int IDGenerator = 20000;
    private int ID;

    private Driver driver;
    private Route route;
    private int PPK;
    private Schedule schedule;
    private int riderCapacity;

    private Time timeOfArrival;
    private int[] matchedIDs;                   //This is the ID of the requestedTrips that got matched to this ride.
    private List<Stop> rideStops;               //TODO: This is not defined right.
    private int expectedFuelConsumption;

    public TransPoolTrip(Driver driver, Route route, int PPK, Schedule schedule, int riderCapacity) {
        this.ID = IDGenerator++;
        this.driver = driver;
        this.route = route;
        this.PPK = PPK;
        this.schedule = schedule;
        this.riderCapacity = riderCapacity;
    }

    public TransPoolTrip(data.generated.TransPoolTrip JAXBTrip) {
        driver = new Driver(JAXBTrip.getOwner());
        route = new Route(JAXBTrip.getRoute());
        PPK = JAXBTrip.getPPK();
        schedule = new Schedule(JAXBTrip.getScheduling());
        riderCapacity = JAXBTrip.getCapacity();
    }

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
