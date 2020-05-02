package data.transpool;

import data.transpool.structures.Route;
import exceptions.data.time.InvalidTimeException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolTrip {
    private static int IDGenerator = 10000;
    private int ID;
    private String owner;
    private int passengerCapacity;
    private Route route;
    private int PPK;
    private Scheduling schedule;

    private int tripDurationInMinutes;
    private int tripPrice;
    private double averageFuelConsumption;

    public TransPoolTrip(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws InvalidTimeException {
        this.ID = IDGenerator++;
        this.owner = JAXBTransPoolTrip.getOwner();
        this.passengerCapacity = JAXBTransPoolTrip.getCapacity();
        this.PPK = JAXBTransPoolTrip.getPPK();
        this.schedule = new Scheduling(JAXBTransPoolTrip.getScheduling());
        this.route = new Route(JAXBTransPoolTrip);
        calculateTotalPrice();
        calculateTripDuration();
        calculateAverageFuelConsumption();
    }

    private void calculateTotalPrice() {
        tripPrice = 0;
        for (int i = 0; i < route.getNumberOfStops() - 1; i++) {
            TransPoolPath currentRoutePath = route.getPathOfSource(i);
            tripPrice += currentRoutePath.getLength() * PPK;
        }
    }

    private void calculateTripDuration() {
        tripDurationInMinutes = 0;
        for (int i = 0; i < route.getNumberOfStops() - 1; i++) {
            TransPoolPath currentRoutePath = route.getPathOfSource(i);
            tripDurationInMinutes += (double) (60 * currentRoutePath.getLength() / currentRoutePath.getMaxSpeed());
        }
    }

    private void calculateAverageFuelConsumption() {
        double totalFuelConsumption = 0;
        for (int i = 0; i < route.getNumberOfStops() - 1; i++) {
            TransPoolPath currentRoutePath = route.getPathOfSource(i);
            totalFuelConsumption += currentRoutePath.getFuelConsumption();
        }
        averageFuelConsumption = totalFuelConsumption / route.getNumberOfStops();
    }


    public String getOwner() {
        return owner;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public Route getRoute() {
        return route;
    }

    public int getPPK() {
        return PPK;
    }

    public Scheduling getSchedule() {
        return schedule;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String transpoolTripString = "";

        transpoolTripString += "------TransPool Trip------\n";
        transpoolTripString += "TransPool trip ID: " + ID + "\n";
        transpoolTripString += "Driver name: " + owner + "\n";
        transpoolTripString += "Passenger capacity: " + passengerCapacity + "\n";
        transpoolTripString += "Route: " + route + "\n";
        transpoolTripString += "Schedule: " + schedule + "\n";
        transpoolTripString += "Trip duration (in minutes): " + tripDurationInMinutes + "\n";
        transpoolTripString += "Average fuel consumption: " + averageFuelConsumption + "\n";
        transpoolTripString += "Price per kilometer: " + PPK + "\n";
        transpoolTripString += "Total trip price: " + tripPrice + "\n";

        return transpoolTripString;
    }
}
