package data.transpool.trip.offer.graph;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.data.BasicTripOfferData;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

//A part of a trip offer.
public class SubTripOffer extends BasicTripOfferData {
    //Place
    private ObjectProperty<Stop> sourceStop;
    private ObjectProperty<Stop> destinationStop;

    //Static data
    private ObjectProperty<LocalTime> timeOfArrivalAtDestination;
    private ObjectProperty<LocalTime> timeOfDepartureFromSource;

    //Dynamic data
    private IntegerProperty passengerCapacity;
    private ObservableList<BasicTripRequest> currentRequests;

    public SubTripOffer(Path path, TripOffer tripOffer) {
        super(tripOffer);
        this.sourceStop = new SimpleObjectProperty<>(path.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(path.getDestinationStop());
        this.timeOfDepartureFromSource = new SimpleObjectProperty<>(tripOffer.getDepartureTimeAtStop(sourceStop.get()));
        this.timeOfArrivalAtDestination = new SimpleObjectProperty<>(tripOffer.getDepartureTimeAtStop(destinationStop.get()));

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

    @Override
    public void initialize() {

    }
}
