package data.transpool.trips;

import data.transpool.map.TransPoolMap;
import data.transpool.map.Path;
import data.transpool.map.Stop;
import data.transpool.user.*;
import exceptions.data.TransPoolDataException;

import java.util.List;

public class TransPoolTrip {
    private static int IDGenerator = 20000;
    private int ID;

    private Driver driver;
    private Route route;
    private int AveragePPK;
    private Scheduling scheduling;
    private int riderCapacity;

    private int totalPrice;
    private Time timeOfArrival;
    private int[] matchedIDs;                   //This is the ID of the requestedTrips that got matched to this ride.
    private List<Stop> rideStops;               //TODO: This is not defined right.
    private int expectedFuelConsumption;

    public TransPoolTrip(Driver driver, Route route, int AveragePPK, Scheduling scheduling, int riderCapacity) {
        this.ID = IDGenerator++;
        this.driver = driver;
        this.route = route;
        this.AveragePPK = AveragePPK;
        this.scheduling = scheduling;
        this.riderCapacity = riderCapacity;
    }

    public TransPoolTrip(data.jaxb.TransPoolTrip JAXBTrip, TransPoolMap map) throws TransPoolDataException {
        this.ID = IDGenerator++;
        this.driver = new Driver(JAXBTrip.getOwner());
        this.route = new Route(JAXBTrip.getRoute(), map);
        this.AveragePPK = JAXBTrip.getPPK();
        this.scheduling = new Scheduling(JAXBTrip.getScheduling());
        this.riderCapacity = JAXBTrip.getCapacity();
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

    public List<Path> getRouteAsList() {
        return route.getRoute();
    }

    public int getAveragePPK() {
        return AveragePPK;
    }

    public Scheduling getScheduling() {
        return scheduling;
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

    @Override
    public String toString() {
        return
                "\n---TransPoolTrip:---" +
                "\nTrip ID: " + ID +
                "\n" + driver +
                "\n" + route +
                "\nPrice per KM: " + AveragePPK +
                "\n" + scheduling +
                "\nRider capacity: " + riderCapacity +
                "\nTime of Arrival: " + timeOfArrival +
                "\nExpected Fuel Consumption: " + expectedFuelConsumption;
    }
}
