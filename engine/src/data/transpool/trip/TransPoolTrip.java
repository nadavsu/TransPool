package data.transpool.trip;

import data.transpool.map.Path;
import data.transpool.user.TransPoolDriver;
import exception.TransPoolRunTimeException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.*;

/**
 * Contains the data for a TransPool trip offered by TransPool drivers.
 */
public class TransPoolTrip {
    private static int IDGenerator = 10000;

    private IntegerProperty offerID;
    private ObjectProperty<TransPoolDriver> transpoolDriver;
    private IntegerProperty passengerCapacity;
    private ObjectProperty<Route> route;
    private IntegerProperty PPK;
    private ObjectProperty<Scheduling> schedule;
    private IntegerProperty rating;

    private IntegerProperty tripDurationInMinutes;
    private IntegerProperty tripPrice;
    private DoubleProperty averageFuelConsumption;

    private ObservableList<RiderStatus> allRiderStatuses;

    public TransPoolTrip(String driverName, LocalTime departureTime, int dayStart, String recurrences, int riderCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        this.offerID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolDriver = new SimpleObjectProperty<>(new TransPoolDriver(driverName));
        this.passengerCapacity = new SimpleIntegerProperty(riderCapacity);
        this.route = new SimpleObjectProperty<>(new Route(route));
        this.PPK = new SimpleIntegerProperty(PPK);
        this.schedule = new SimpleObjectProperty<>(new Scheduling(dayStart, departureTime, recurrences));
        this.rating = new SimpleIntegerProperty(0);

        this.tripPrice = new SimpleIntegerProperty();
        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();

        this.allRiderStatuses = FXCollections.observableArrayList();

        initializeTripData();
    }

    public TransPoolTrip(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws TransPoolDataException {
        this.offerID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolDriver = new SimpleObjectProperty<>(new TransPoolDriver(JAXBTransPoolTrip.getOwner()));
        this.passengerCapacity = new SimpleIntegerProperty(JAXBTransPoolTrip.getCapacity());
        this.PPK = new SimpleIntegerProperty(JAXBTransPoolTrip.getPPK());
        this.schedule = new SimpleObjectProperty<>(new Scheduling(JAXBTransPoolTrip.getScheduling()));
        this.route = new SimpleObjectProperty<>(new Route(JAXBTransPoolTrip));
        this.rating = new SimpleIntegerProperty(0);

        this.tripDurationInMinutes = new SimpleIntegerProperty(0);
        this.tripPrice = new SimpleIntegerProperty(0);
        this.averageFuelConsumption = new SimpleDoubleProperty(0);

        this.allRiderStatuses = FXCollections.observableArrayList();

        initializeTripData();
    }

    private void initializeTripData() {
        calculatePriceOfRoute();
        calculateTripDuration();
        calculateAverageFuelConsumption();
    }

    private void calculatePriceOfRoute() {
        tripPrice.set(route
                .get()
                .getUsedPaths()
                .stream()
                .mapToInt(p -> p.getLength() * PPK.get())
                .sum()
        );
    }

    private void calculateTripDuration() {
        tripDurationInMinutes.set(route
                .get()
                .getUsedPaths()
                .stream()
                .mapToInt(Path::getPathTime)
                .sum()
        );
    }

    private void calculateAverageFuelConsumption() {
        averageFuelConsumption.set(route
                .get()
                .getUsedPaths()
                .stream()
                .mapToDouble(Path::getFuelConsumption)
                .average()
                .orElse(0)
        );
    }

    public boolean containsSubRoute(String source, String destination) {
        return route.get().containsSubRoute(source, destination);
    }

    /**
     * Updates the trip after there is a found match for it.
     * Adds the details from the match to the status list, decrements the passenger capacity.
     * @param matchedRequest - the mathed request.
     */
    public void updateAfterMatch(MatchedTransPoolTripRequest matchedRequest) {
        if (passengerCapacity.get() == 0) {
            throw new TransPoolRunTimeException();
        }
        passengerCapacity.set(passengerCapacity.get() - 1);
        allRiderStatuses.add(new RiderStatus(matchedRequest));
    }

    public int getOfferID() {
        return offerID.get();
    }

    public IntegerProperty offerIDProperty() {
        return offerID;
    }

    public TransPoolDriver getTranspoolDriver() {
        return transpoolDriver.get();
    }

    public ObjectProperty<TransPoolDriver> transpoolDriverProperty() {
        return transpoolDriver;
    }

    public void setTranspoolDriver(TransPoolDriver transpoolDriver) {
        this.transpoolDriver.set(transpoolDriver);
    }

    public int getPassengerCapacity() {
        return passengerCapacity.get();
    }

    public IntegerProperty passengerCapacityProperty() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity.set(passengerCapacity);
    }

    public Route getRoute() {
        return route.get();
    }

    public ObjectProperty<Route> routeProperty() {
        return route;
    }

    public void setRoute(Route route) {
        this.route.set(route);
    }

    public int getPPK() {
        return PPK.get();
    }

    public IntegerProperty PPKProperty() {
        return PPK;
    }

    public void setPPK(int PPK) {
        this.PPK.set(PPK);
    }

    public Scheduling getSchedule() {
        return schedule.get();
    }

    public ObjectProperty<Scheduling> scheduleProperty() {
        return schedule;
    }

    public void setSchedule(Scheduling schedule) {
        this.schedule.set(schedule);
    }

    public int getTripDurationInMinutes() {
        return tripDurationInMinutes.get();
    }

    public IntegerProperty tripDurationInMinutesProperty() {
        return tripDurationInMinutes;
    }

    public void setTripDurationInMinutes(int tripDurationInMinutes) {
        this.tripDurationInMinutes.set(tripDurationInMinutes);
    }

    public int getTripPrice() {
        return tripPrice.get();
    }

    public IntegerProperty tripPriceProperty() {
        return tripPrice;
    }

    public void setTripPrice(int tripPrice) {
        this.tripPrice.set(tripPrice);
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption.get();
    }

    public DoubleProperty averageFuelConsumptionProperty() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(double averageFuelConsumption) {
        this.averageFuelConsumption.set(averageFuelConsumption);
    }

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public ObservableList<RiderStatus> getAllRiderStatuses() {
        return allRiderStatuses;
    }

    public void setAllRiderStatuses(ObservableList<RiderStatus> allRiderStatuses) {
        this.allRiderStatuses = allRiderStatuses;
    }

    @Override
    public String toString() {
        return transpoolDriver.get() + " - " + offerID.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransPoolTrip)) return false;
        TransPoolTrip that = (TransPoolTrip) o;
        return offerID.get() == that.offerID.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerID.get());
    }

    /**
     * The status of the matched requests.
     * Contains ID, source and destination.
     */
    public static class RiderStatus {
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
