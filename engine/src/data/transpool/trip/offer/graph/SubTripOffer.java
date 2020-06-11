package data.transpool.trip.offer.graph;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.Scheduling;
import data.transpool.trip.offer.data.BasicTripOfferData;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

//Todo: something with the scheduling that is inherited from BTOD
//Todo: you stopped here with the next and prev occurrence and the getNextOccurrence in possibleRoute.
//A part of a trip offer.
public class SubTripOffer extends BasicTripOfferData {
    private SubTripOffer nextOccurrence;
    private SubTripOffer prevOccurrence;

    //Place
    private ObjectProperty<Stop> sourceStop;
    private ObjectProperty<Stop> destinationStop;

    //Time data
    private IntegerProperty day;
    private StringProperty recurrences;
    private ObjectProperty<LocalTime> timeOfDepartureFromSource;
    private ObjectProperty<LocalTime> timeOfArrivalAtDestination;

    //Dynamic data
    private IntegerProperty passengerCapacity;
    private ObservableList<BasicTripRequest> currentRequests;

    //Constructor used for initializing first occurrence
    public SubTripOffer(Path path, TripOffer tripOffer) {
        super(tripOffer);
        this.sourceStop = new SimpleObjectProperty<>(path.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(path.getDestinationStop());

        this.day = new SimpleIntegerProperty(tripOffer.getScheduling().getDayStart());
        this.timeOfDepartureFromSource = new SimpleObjectProperty<>(tripOffer.getDepartureTimeAtStop(sourceStop.get()));
        this.timeOfArrivalAtDestination = new SimpleObjectProperty<>(tripOffer.getDepartureTimeAtStop(destinationStop.get()));
        this.recurrences = new SimpleStringProperty(tripOffer.getScheduling().getRecurrences());

        this.tripPrice = new SimpleIntegerProperty(path.getLength() * PPK.get());
        this.averageFuelConsumption = new SimpleDoubleProperty(path.getFuelConsumption());
        this.tripDurationInMinutes = new SimpleIntegerProperty(path.getPathTime());


        this.currentRequests = FXCollections.observableArrayList(tripOffer.getAllMatchedRequestsData());
       this.passengerCapacity = new SimpleIntegerProperty(tripOffer.getPassengerCapacity());

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

    public LocalTime getTimeOfArrivalAtDestination() {
        return timeOfArrivalAtDestination.get();
    }

    public ObjectProperty<LocalTime> timeOfArrivalAtDestinationProperty() {
        return timeOfArrivalAtDestination;
    }

    public LocalTime getTimeOfDepartureFromSource() {
        return timeOfDepartureFromSource.get();
    }

    public ObjectProperty<LocalTime> timeOfDepartureFromSourceProperty() {
        return timeOfDepartureFromSource;
    }

    public ObservableList<BasicTripRequest> getCurrentRequests() {
        return currentRequests;
    }

    public int getDay() {
        return day.get();
    }

    public IntegerProperty dayProperty() {
        return day;
    }

    public String getRecurrences() {
        return recurrences.get();
    }

    public StringProperty recurrencesProperty() {
        return recurrences;
    }

    public boolean isBefore(SubTripOffer other) {
        if (this.day.get() < other.day.get()) {
            return true;
        } else if (this.day.get() == other.day.get()) {
            return this.timeOfArrivalAtDestination.get().isBefore(other.timeOfDepartureFromSource.get())
                    || this.timeOfArrivalAtDestination.get().equals(other.timeOfArrivalAtDestination.get());
        } else {
            return false;
        }
    }

    public SubTripOffer getNextOcurrence() {
        return nextOccurrence;
    }

    @Override
    public String toString() {
        return "Depart from " + getSourceStop() +
                " with " + getTransPoolDriver() +
                " at time " + getTimeOfDepartureFromSource() +
                " and arrive at " + getDestinationStop() +
                " at time " + getTimeOfArrivalAtDestination();
    }
}
