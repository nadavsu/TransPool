package data.transpool.trips;

import data.transpool.map.Path;
import data.transpool.structures.Route;
import data.transpool.user.TransPoolDriver;
import exceptions.TransPoolRunTimeException;
import exceptions.file.TransPoolFileDataException;
import exceptions.time.InvalidTimeException;

import java.util.*;

public class TransPoolTrip {
    private static int IDGenerator = 10000;
    private int ID;
    private TransPoolDriver transpoolDriver;
    private int passengerCapacity;
    private Route route;
    private int PPK;
    private Scheduling schedule;

    private int tripDurationInMinutes;
    private int tripPrice;
    private double averageFuelConsumption;

    private List<RiderStatus> allRiderStatuses = new ArrayList<>();

    public TransPoolTrip(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws InvalidTimeException, TransPoolFileDataException {
        this.ID = IDGenerator++;
        this.transpoolDriver = new TransPoolDriver(JAXBTransPoolTrip.getOwner());
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
                .mapToInt(Path::getPathTime)
                .sum();
    }

    private void calculateAverageFuelConsumption() {
        averageFuelConsumption = route
                .getUsedPaths()
                .stream()
                .mapToDouble(Path::getFuelConsumption)
                .average()
                .orElse(0);
    }

    public boolean containsSubRoute(String source, String destination) {
        return route.containsSubRoute(source, destination);
    }

    public void updateAfterMatch(MatchedTransPoolTripRequest matchedRequest) {
        if (passengerCapacity == 0) {
            throw new TransPoolRunTimeException();
        }
        passengerCapacity--;
        allRiderStatuses.add(new RiderStatus(matchedRequest));
    }

    public TransPoolDriver getOwner() {
        return transpoolDriver;
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
        transpoolTripString += "Driver name: " + transpoolDriver + "\n";
        transpoolTripString += "Passenger capacity: " + passengerCapacity + "\n";
        transpoolTripString += "Route: " + route + "\n";
        transpoolTripString += "Schedule: " + schedule + "\n";
        transpoolTripString += "Trip duration (in minutes): " + tripDurationInMinutes + "\n";
        transpoolTripString += "Average fuel consumption: " + averageFuelConsumption + "\n";
        transpoolTripString += "Price per kilometer: " + PPK + "\n";
        transpoolTripString += "Total trip price: " + tripPrice + "\n";
        transpoolTripString += "Rider details: " + allRiderStatuses + "\n";

        return transpoolTripString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransPoolTrip)) return false;
        TransPoolTrip that = (TransPoolTrip) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    private class RiderStatus {
        private int riderID;
        private String source;
        private String destination;

        public RiderStatus(MatchedTransPoolTripRequest matchedRequest) {
            this.riderID = matchedRequest.getTranspoolRider().getID();
            this.source = matchedRequest.getSource();
            this.destination = matchedRequest.getDestination();
        }

        @Override
        public String toString() {
            return "Rider " + riderID + " gets on at " + source + " and gets off at " + destination;
        }
    }
}
