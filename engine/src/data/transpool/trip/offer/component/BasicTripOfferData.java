package data.transpool.trip.offer.component;

import data.transpool.time.component.Scheduling;
import data.transpool.user.account.TransPoolDriver;
import javafx.beans.property.*;

/**
 * Contains basic data of a trip offer - be it a sub-trip offer or a normal trip offer.
 */
public abstract class BasicTripOfferData implements BasicTripOffer {
    private static int IDGenerator = 100000;
    protected int offerID;
    protected TransPoolDriver transpoolDriver;
    protected int PPK;
    protected int maxPassengerCapacity;

    //Initialized at children
    protected Scheduling schedule;
    protected int tripDurationInMinutes;
    protected int tripPrice;
    protected double averageFuelConsumption;

    public BasicTripOfferData(String driverName, int PPK, int maxPassengerCapacity) {
        this.offerID = IDGenerator++;
        this.transpoolDriver = new TransPoolDriver(driverName);
        this.PPK = PPK;
        this.maxPassengerCapacity = maxPassengerCapacity;
    }

    public BasicTripOfferData(BasicTripOffer other) {
        this.offerID = other.getOfferID();
        this.transpoolDriver = new TransPoolDriver(other.getTransPoolDriver());
        this.PPK = other.getPPK();
        this.schedule = new Scheduling(other.getScheduling());
        this.maxPassengerCapacity = other.getMaxPassengerCapacity();

    }

    @Override
    public int getOfferID() {
        return offerID;
    }

    @Override
    public TransPoolDriver getTransPoolDriver() {
        return transpoolDriver;
    }

    @Override
    public void setTransPoolDriver(TransPoolDriver transpoolDriver) {
        this.transpoolDriver = transpoolDriver;
    }

    @Override
    public int getPPK() {
        return PPK;
    }

    @Override
    public void setPPK(int PPK) {
        this.PPK = PPK;
    }

    @Override
    public Scheduling getScheduling() {
        return schedule;
    }

    @Override
    public void setScheduling(Scheduling schedule) {
        this.schedule = schedule;
    }

    @Override
    public int getTripDurationInMinutes() {
        return tripDurationInMinutes;
    }

    @Override
    public int getPrice() {
        return tripPrice;
    }

    @Override
    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    @Override
    public int getMaxPassengerCapacity() {
        return maxPassengerCapacity;
    }

    @Override
    public void setMaxPassengerCapacity(int maxPassengerCapacity) {
        this.maxPassengerCapacity = maxPassengerCapacity;
    }
}
