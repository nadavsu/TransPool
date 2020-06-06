package data.transpool.trip.offer;

import data.transpool.map.Path;
import data.transpool.trip.Scheduling;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.Route;
import data.transpool.trip.request.BasicTripRequestData;
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
public class TripOfferData extends BasicTripOfferData implements TripOffer {

    private IntegerProperty passengerCapacity;
    private IntegerProperty rating;
    private ObservableList<BasicTripRequest> allMatchedRequestsData;

    public TripOfferData(String driverName, LocalTime departureTime, int dayStart, String recurrences, int passengerCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        super(driverName, departureTime, dayStart, recurrences, PPK);
        this.passengerCapacity = new SimpleIntegerProperty(passengerCapacity);
        this.route = new SimpleObjectProperty<>(new Route(route));
        this.rating = new SimpleIntegerProperty(0);
        this.allMatchedRequestsData = FXCollections.observableArrayList();
        initialize();
    }

    public TripOfferData(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws TransPoolDataException {
        super(JAXBTransPoolTrip);
        this.passengerCapacity = new SimpleIntegerProperty(JAXBTransPoolTrip.getCapacity());
        this.route = new SimpleObjectProperty<>(new Route(JAXBTransPoolTrip));
        this.rating = new SimpleIntegerProperty(0);
        this.allMatchedRequestsData = FXCollections.observableArrayList();
        initialize();
    }

    @Override
    public void initialize() {
        this.tripPrice.set(calculatePriceOfRoute(getRoute(), getPPK()));
        this.tripDurationInMinutes.set(calculateTripDuration(getRoute()));
        this.averageFuelConsumption.set(calculateAverageFuelConsumption(getRoute()));
    }


    @Override
    public int getDriverRating() {
        return rating.get();
    }

    @Override
    public void setDriverRating(int rating) {
        this.rating.set(rating);
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity.get();
    }

    @Override
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity.set(passengerCapacity);
    }

    @Override
    public ObservableList<BasicTripRequest> getAllMatchedRequestsData() {
        return allMatchedRequestsData;
    }

    @Override
    public void setAllMatchedRequestsData(ObservableList<BasicTripRequest> allMatchedRequestsData) {
        this.allMatchedRequestsData = allMatchedRequestsData;
    }

    @Override
    public boolean containsSubRoute(String source, String destination) {
        return route.get().containsSubRoute(source, destination);
    }

    /**
     * Updates the trip after there is a found match for it.
     * Adds the details from the match to the status list, decrements the passenger capacity.
     * @param matchedRequest - the mathed request.
     */
    @Override
    public void updateAfterMatch(MatchedTripRequest matchedRequest) {
        if (passengerCapacity.get() == 0) {
            throw new TransPoolRunTimeException();
        }
        passengerCapacity.set(passengerCapacity.get() - 1);
        allMatchedRequestsData.add(matchedRequest);
    }

    @Override
    public IntegerProperty ratingProperty() {
        return rating;
    }

    @Override
    public IntegerProperty passengerCapacityProperty() {
        return passengerCapacity;
    }

    @Override
    public String toString() {
        return transpoolDriver.get() + " - " + offerID.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripOfferData)) return false;
        TripOfferData that = (TripOfferData) o;
        return offerID.get() == that.offerID.get();
    }
    @Override
    public int hashCode() {
        return Objects.hash(offerID.get());
    }
}
