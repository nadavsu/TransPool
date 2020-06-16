package data.transpool.trip.offer.data;

import data.transpool.trip.Recurrence;
import data.transpool.trip.Scheduling;
import data.transpool.user.TransPoolDriver;
import data.transpool.util.Util;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;

public abstract class BasicTripOfferData implements BasicTripOffer {
    private static int IDGenerator = 100000;
    protected IntegerProperty offerID;
    protected ObjectProperty<TransPoolDriver> transpoolDriver;
    protected IntegerProperty PPK;
    protected ObjectProperty<Scheduling> schedule;

    //Initialized at children
    protected IntegerProperty tripDurationInMinutes;
    protected IntegerProperty tripPrice;
    protected DoubleProperty averageFuelConsumption;
    protected IntegerProperty maxPassengerCapacity;

    public BasicTripOfferData(String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences, int PPK, int maxPassengerCapacity) throws TransPoolDataException {
        this.offerID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolDriver = new SimpleObjectProperty<>(new TransPoolDriver(driverName));
        this.PPK = new SimpleIntegerProperty(PPK);
        this.schedule = new SimpleObjectProperty<>(new Scheduling(dayStart, departureTime, recurrences));

        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.tripPrice = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();
        this.maxPassengerCapacity = new SimpleIntegerProperty(maxPassengerCapacity);
    }

    public BasicTripOfferData(data.jaxb.TransPoolTrip JAXBTransPoolTrip) throws TransPoolDataException {
        this.offerID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolDriver = new SimpleObjectProperty<>(new TransPoolDriver(JAXBTransPoolTrip.getOwner()));
        this.PPK = new SimpleIntegerProperty(JAXBTransPoolTrip.getPPK());
        this.schedule = new SimpleObjectProperty<>(new Scheduling(JAXBTransPoolTrip.getScheduling()));
        this.maxPassengerCapacity = new SimpleIntegerProperty(JAXBTransPoolTrip.getCapacity());

        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.tripPrice = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();
    }

    public BasicTripOfferData(BasicTripOffer other) {
        this.offerID = new SimpleIntegerProperty(other.getOfferID());
        this.transpoolDriver = new SimpleObjectProperty<>(other.getTransPoolDriver());
        this.PPK = new SimpleIntegerProperty(other.getPPK());
        this.schedule = new SimpleObjectProperty<>(new Scheduling(other.getScheduling()));
        this.maxPassengerCapacity = new SimpleIntegerProperty(other.getMaxPassengerCapacity());

        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.tripPrice = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();

    }

    @Override
    public IntegerProperty offerIDProperty() {
        return offerID;
    }

    @Override
    public int getOfferID() {
        return offerID.get();
    }

    @Override
    public TransPoolDriver getTransPoolDriver() {
        return transpoolDriver.get();
    }

    @Override
    public void setTransPoolDriver(TransPoolDriver transpoolDriver) {
        this.transpoolDriver.set(transpoolDriver);
    }

    @Override
    public int getPPK() {
        return PPK.get();
    }

    @Override
    public void setPPK(int PPK) {
        this.PPK.set(PPK);
    }

    @Override
    public Scheduling getScheduling() {
        return schedule.get();
    }

    @Override
    public void setScheduling(Scheduling schedule) {
        this.schedule.set(schedule);
    }

    @Override
    public ObjectProperty<Scheduling> schedulingProperty() {
        return schedule;
    }

    @Override
    public ObjectProperty<TransPoolDriver> transpoolDriverProperty() {
        return transpoolDriver;
    }

    @Override
    public IntegerProperty PPKProperty() {
        return PPK;
    }

    @Override
    public int getTripDurationInMinutes() {
        return tripDurationInMinutes.get();
    }

    @Override
    public int getPrice() {
        return tripPrice.get();
    }


    @Override
    public double getAverageFuelConsumption() {
        return averageFuelConsumption.get();
    }

    @Override
    public IntegerProperty tripDurationInMinutesProperty() {
        return tripDurationInMinutes;
    }

    @Override
    public IntegerProperty priceProperty() {
        return tripPrice;
    }

    @Override
    public DoubleProperty averageFuelConsumptionProperty() {
        return averageFuelConsumption;
    }

    @Override
    public int getMaxPassengerCapacity() {
        return maxPassengerCapacity.get();
    }

    @Override
    public void setMaxPassengerCapacity(int maxPassengerCapacity) {
        this.maxPassengerCapacity.set(maxPassengerCapacity);
    }

    @Override
    public IntegerProperty maxPassengerCapacityProperty() {
        return maxPassengerCapacity;
    }
}
