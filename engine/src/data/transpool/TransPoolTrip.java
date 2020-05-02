package data.transpool;

import data.transpool.structures.Route;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;

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

    public TransPoolTrip(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws InvalidTimeException, TransPoolDataException {
        this.ID = IDGenerator++;
        this.owner = JAXBTransPoolTrip.getOwner();
        this.passengerCapacity = JAXBTransPoolTrip.getCapacity();
        this.PPK = JAXBTransPoolTrip.getPPK();
        this.schedule = new Scheduling(JAXBTransPoolTrip.getScheduling());
        this.route = new Route(JAXBTransPoolTrip);

        calculatePriceOfRoute();
        calculateTripDuration();
        calculateAverageFuelConsumption();
    }

    private void calculatePriceOfRoute() {
        tripPrice = route.
                getUsedPaths()
                .stream()
                .mapToInt(p -> p.getLength() * PPK)
                .sum();
    }

    private void calculateTripDuration() {
        tripDurationInMinutes = route.
                getUsedPaths()
                .stream()
                .mapToInt(TransPoolPath::getPathTime)
                .sum();
    }

    private void calculateAverageFuelConsumption() {
        averageFuelConsumption = route
                .getUsedPaths()
                .stream()
                .mapToDouble(TransPoolPath::getFuelConsumption)
                .average()
                .orElse(0);
    }

    public boolean containsSubRoute(String source, String destination) {
        return route.containsSubRoute(source, destination);
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
