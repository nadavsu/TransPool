package data.transpool.trip.offer.data;

import data.transpool.map.BasicMap;
import data.transpool.map.component.Stop;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.Route;
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

    private ObjectProperty<Route> route;
    private IntegerProperty passengerCapacity;
    private ObservableList<BasicTripRequest> allMatchedRequestsData;
    private Map<Stop, LocalTime> timeTable;

    public TripOfferData(BasicMap map, String driverName, LocalTime departureTime, int dayStart, String recurrences, int passengerCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        super(driverName, departureTime, dayStart, recurrences, PPK);
        this.timeTable = new HashMap<>();
        this.passengerCapacity = new SimpleIntegerProperty(passengerCapacity);
        this.route = new SimpleObjectProperty<>(new Route(route, map));
        this.allMatchedRequestsData = FXCollections.observableArrayList();
        initialize();
    }

    public TripOfferData(data.jaxb.TransPoolTrip JAXBTransPoolTrip, BasicMap map) throws TransPoolDataException {
        super(JAXBTransPoolTrip);
        this.timeTable = new HashMap<>();
        this.passengerCapacity = new SimpleIntegerProperty(JAXBTransPoolTrip.getCapacity());
        this.route = new SimpleObjectProperty<>(new Route(JAXBTransPoolTrip, map));
        this.allMatchedRequestsData = FXCollections.observableArrayList();
        initialize();
    }

    public void initialize() {
        this.tripPrice.set(getRoute().calculatePriceOfRoute(PPK.get()));
        this.tripDurationInMinutes.set(getRoute().calculateTripDuration());
        this.averageFuelConsumption.set(getRoute().calculateAverageFuelConsumption());
        initializeTimeTable();
    }

    private void initializeTimeTable() {
        int i;
        LocalTime timeAtStop = getScheduling().getDepartureTime();
        for (i = 0; i < route.get().getLength() - 1; i++) {
            Stop currentStop = route.get().getStop(i);

            timeTable.put(currentStop, timeAtStop);
            int pathTime = route.get().getPath(i).getPathTime();
            timeAtStop = timeAtStop.plusMinutes(pathTime);
        }
        timeTable.put(route.get().getStop(i), timeAtStop);
    }

    @Override
    public Route getRoute() {
        return route.get();
    }

    @Override
    public void setRoute(Route route) {
        this.route.set(route);
    }

    @Override
    public ObjectProperty<Route> routeProperty() {
        return route;
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
    public LocalTime getDepartureTimeAtStop(Stop stop) {
        return timeTable.get(stop);
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
