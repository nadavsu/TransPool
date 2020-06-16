package data.transpool.trip.offer.graph;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.Recurrence;
import data.transpool.trip.offer.data.BasicTripOfferData;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
//Todo: maybe remove TimeDay
//A part of a trip offer.
public class SubTripOffer extends BasicTripOfferData {

    private int subTripOfferID;

    //Place
    private ObjectProperty<Stop> sourceStop;
    private ObjectProperty<Stop> destinationStop;

    //Time data
    private ObjectProperty<TimeDay> timeOfDepartureFromSource;
    private ObjectProperty<TimeDay> timeOfArrivalAtDestination;
    private ObjectProperty<Recurrence> recurrences;

    //Holds dynamic data
    private Map<Integer, Integer> dayToCapacityMap;

    //Dynamic data
    private ObservableList<BasicTripRequest> currentRequests;

    public SubTripOffer(int ID, Path path, TripOffer tripOffer) {
        super(tripOffer);
        this.subTripOfferID = ID;
        this.currentRequests = FXCollections.observableArrayList(tripOffer.getAllMatchedRequestsData());
        this.dayToCapacityMap = new HashMap<>();

        this.sourceStop = new SimpleObjectProperty<>(path.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(path.getDestinationStop());

        this.timeOfDepartureFromSource = new SimpleObjectProperty<>(new TimeDay(
                tripOffer.getDepartureTimeAtStop(sourceStop.get()),
                tripOffer.getScheduling().getDayStart()
        ));
        this.timeOfArrivalAtDestination = new SimpleObjectProperty<>(new TimeDay(
                tripOffer.getDepartureTimeAtStop(destinationStop.get()),
                tripOffer.getScheduling().getDayStart()
        ));
        this.recurrences = new SimpleObjectProperty<>(tripOffer.getScheduling().getRecurrences());

        this.tripPrice = new SimpleIntegerProperty(path.getLength() * PPK.get());
        this.averageFuelConsumption = new SimpleDoubleProperty(path.getFuelConsumption());
        this.tripDurationInMinutes = new SimpleIntegerProperty(path.getPathTime());
    }

    public SubTripOffer(SubTripOffer other) {
        super(other);
        this.subTripOfferID = other.subTripOfferID;
        this.dayToCapacityMap = new HashMap<>(other.getDayToCapacityMap());
        this.sourceStop = new SimpleObjectProperty<>(other.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(other.getDestinationStop());

        this.timeOfDepartureFromSource = new SimpleObjectProperty<>(new TimeDay(other.timeOfDepartureFromSource.get()));
        this.timeOfArrivalAtDestination = new SimpleObjectProperty<>(new TimeDay(other.timeOfArrivalAtDestination.get()));
        this.recurrences = new SimpleObjectProperty<>(other.getRecurrences());

        this.tripPrice = new SimpleIntegerProperty(other.getPrice());
        this.averageFuelConsumption = new SimpleDoubleProperty(other.getAverageFuelConsumption());
        this.tripDurationInMinutes = new SimpleIntegerProperty(other.getTripDurationInMinutes());
    }

    public int getSubTripOfferID() {
        return subTripOfferID;
    }

    public Stop getSourceStop() {
        return sourceStop.get();
    }

    public Stop getDestinationStop() {
        return destinationStop.get();
    }

    public int getPassengerCapacityOnDay(int day) {
        return dayToCapacityMap.get(day);
    }

    public void setPassengerCapacityOnDay(int day, int passengerCapacity) {
        this.dayToCapacityMap.put(day, passengerCapacity);
    }

    public TimeDay getTimeOfArrivalAtDestination() {
        return timeOfArrivalAtDestination.get();
    }

    public TimeDay getTimeOfDepartureFromSource() {
        return timeOfDepartureFromSource.get();
    }

    public ObservableList<BasicTripRequest> getCurrentRequests() {
        return currentRequests;
    }

    public Map<Integer, Integer> getDayToCapacityMap() {
        return dayToCapacityMap;
    }

    public int getDayStart() {
        return timeOfDepartureFromSource.get().getDay();
    }

    public Recurrence getRecurrences() {
        return schedule.get().getRecurrences();
    }

    public boolean isCurrentlyHappening() {
        return TransPoolData.currentTime.getTime().isAfter(getTimeOfDepartureFromSource().getTime())
                && TransPoolData.currentTime.getTime().isBefore(getTimeOfArrivalAtDestination().getTime())
                && isRecurrenceOnDay(TransPoolData.currentTime.getDay());
    }

    public boolean isRecurrenceOnDay(int day) {
        return getRecurrences().isOnDay(day, getDayStart());
    }

    public void updateOnDay(int day) {
        if (dayToCapacityMap.get(day) == null) {
            dayToCapacityMap.put(day, getMaxPassengerCapacity() - 1);
        } else {
            int currentCapacityOnDay = dayToCapacityMap.get(day);
            dayToCapacityMap.put(day, currentCapacityOnDay - 1);
        }
    }

    public boolean getOccurrenceAfter(TimeDay timeDay) {
        if (this.getTimeOfDepartureFromSource().isAfter(timeDay)) {
            return dayToCapacityMap.get(getDayStart()) == null || dayToCapacityMap.get(getDayStart()) > 0 ;

        } else if (!this.recurrences.get().equals(Recurrence.ONE_TIME)) {
            while (this.getTimeOfArrivalAtDestination().isBefore(timeDay)) {
                this.getTimeOfArrivalAtDestination().setNextRecurrence(this.getRecurrences());
                this.getTimeOfDepartureFromSource().setNextRecurrence(this.getRecurrences());
            }
            return dayToCapacityMap.get(getTimeOfDepartureFromSource().getDay()) == null || dayToCapacityMap.get(getTimeOfDepartureFromSource().getDay()) > 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Depart from " + getSourceStop() +
                " with " + getTransPoolDriver() +
                " at " + getTimeOfDepartureFromSource() +
                " and arrive at " + getDestinationStop() +
                " at " + getTimeOfArrivalAtDestination();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTripOffer)) return false;
        SubTripOffer that = (SubTripOffer) o;
        return Objects.equals(sourceStop, that.sourceStop) &&
                Objects.equals(destinationStop, that.destinationStop) &&
                Objects.equals(schedule, that.schedule) &&
                Objects.equals(timeOfArrivalAtDestination, that.timeOfArrivalAtDestination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceStop, destinationStop, schedule, timeOfArrivalAtDestination);
    }

/*    public boolean isBefore(SubTripOffer other) {
        if (this.getDayStart() < other.getDayStart()) {
            return true;
        } else if (this.getDayStart() == other.getDayStart()){
            return this.getTimeOfArrivalAtDestination().isBefore(other.getTimeOfDepartureFromSource())
                    || this.getTimeOfArrivalAtDestination().equals(other.getTimeOfDepartureFromSource());
        } else {
            return false;
        }
    }*/

/*
    public SubTripOffer getOccurrenceAfter(TimeDay timeDay) {
        if (this.getTimeOfDepartureFromSource().isAfter(timeDay)) {
            return this;
        } else if (!this.recurrences.get().equals("OneTime")) {
            if (this.getNextOccurrence() != null) {
                return getNextOccurrence();
            } else {

            }
        } else {
            return null;
        }

        while (getNextOccurrence() != null && !this.getTimeOfDepartureFromSource().isAfter(timeDay)) {

        }
    }*/
}
