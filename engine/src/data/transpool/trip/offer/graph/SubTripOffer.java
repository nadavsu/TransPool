package data.transpool.trip.offer.graph;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.offer.data.BasicTripOfferData;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import exception.data.InvalidDayStartException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

//A part of a trip offer.
public class SubTripOffer extends BasicTripOfferData {
    private SubTripOffer prevOccurrence;
    private SubTripOffer nextOccurrence;

    //Place
    private ObjectProperty<Stop> sourceStop;
    private ObjectProperty<Stop> destinationStop;

    //Time data
    private ObjectProperty<TimeDay> timeOfDepartureFromSource;
    private ObjectProperty<TimeDay> timeOfArrivalAtDestination;
    private StringProperty recurrences;

    //Dynamic data
    private IntegerProperty passengerCapacity;
    private ObservableList<BasicTripRequest> currentRequests;

    //Constructor used for intitializing first occurrence
    public SubTripOffer(Path path, TripOffer tripOffer) {
        super(tripOffer);
//        this.nextOccurrence = null;
//        this.prevOccurrence = null;

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
        this.recurrences = new SimpleStringProperty(tripOffer.getScheduling().getRecurrences());

        this.tripPrice = new SimpleIntegerProperty(path.getLength() * PPK.get());
        this.averageFuelConsumption = new SimpleDoubleProperty(path.getFuelConsumption());
        this.tripDurationInMinutes = new SimpleIntegerProperty(path.getPathTime());


        this.currentRequests = FXCollections.observableArrayList(tripOffer.getAllMatchedRequestsData());
        this.passengerCapacity = new SimpleIntegerProperty(tripOffer.getPassengerCapacity());
    }

    public SubTripOffer(SubTripOffer other) {
        super(other);

        this.sourceStop = new SimpleObjectProperty<>(other.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(other.getDestinationStop());

        this.timeOfDepartureFromSource = new SimpleObjectProperty<>(new TimeDay(other.timeOfDepartureFromSource.get()));
        this.timeOfArrivalAtDestination = new SimpleObjectProperty<>(new TimeDay(other.timeOfArrivalAtDestination.get()));
        this.recurrences = new SimpleStringProperty(other.getRecurrences());

        this.tripPrice = new SimpleIntegerProperty(other.getPrice());
        this.averageFuelConsumption = new SimpleDoubleProperty(other.getAverageFuelConsumption());
        this.tripDurationInMinutes = new SimpleIntegerProperty(other.getTripDurationInMinutes());
    }

    public Stop getSourceStop() {
        return sourceStop.get();
    }

    public ObjectProperty<Stop> sourceStopProperty() {
        return sourceStop;
    }

    public Stop getDestinationStop() {
        return destinationStop.get();
    }

    public ObjectProperty<Stop> destinationStopProperty() {
        return destinationStop;
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

    public ObjectProperty<TimeDay> timeOfArrivalAtDestinationProperty() {
        return timeOfArrivalAtDestination;
    }

    public TimeDay getTimeOfArrivalAtDestination() {
        return timeOfArrivalAtDestination.get();
    }

    public TimeDay getTimeOfDepartureFromSource() {
        return timeOfDepartureFromSource.get();
    }

    public ObjectProperty<TimeDay> timeOfDepartureFromSourceProperty() {
        return timeOfDepartureFromSource;
    }

    public ObservableList<BasicTripRequest> getCurrentRequests() {
        return currentRequests;
    }


    public int getDay() {
        return timeOfDepartureFromSource.get().getDay();
    }

    public String getRecurrences() {
        return schedule.get().getRecurrences();
    }

    //---------------------

    public SubTripOffer getPrevOccurrence() {
        return prevOccurrence;
    }

    public void setPrevOccurrence(SubTripOffer prevOccurrence) {
        this.prevOccurrence = prevOccurrence;
    }

    public SubTripOffer getNextOccurrence() {
        return nextOccurrence;
    }

    public void setNextOccurrence(SubTripOffer nextOccurrence) {
        this.nextOccurrence = nextOccurrence;
    }

    //--------------------

    public boolean isBefore(SubTripOffer other) {
        if (this.getDay() < other.getDay()) {
            return true;
        } else if (this.getDay() == other.getDay()){
            return this.getTimeOfArrivalAtDestination().isBefore(other.getTimeOfDepartureFromSource())
                    || this.getTimeOfArrivalAtDestination().equals(other.getTimeOfDepartureFromSource());
        } else {
            return false;
        }
    }

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

    /*public boolean getOccurrenceAfter(TimeDay timeDay) {
        if (this.getTimeOfDepartureFromSource().isAfter(timeDay)) {
            return true;
        } else if (!this.recurrences.get().equals("OneTime")) {
            while (this.getTimeOfArrivalAtDestination().isBefore(timeDay)) {
                this.getTimeOfArrivalAtDestination().setNextRecurrence(this.getRecurrences());
                this.getTimeOfDepartureFromSource().setNextRecurrence(this.getRecurrences());
            }
            return true;
        } else {
            return false;
        }
    }*/

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
}
