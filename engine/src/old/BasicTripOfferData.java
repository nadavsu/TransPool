package old;

import data.transpool.time.component.Recurrence;
import data.transpool.trip.offer.component.BasicTripOffer;
import data.transpool.user.account.TransPoolDriver;

/**
 * Contains basic data of a trip offer - be it a sub-trip offer or a normal trip offer.
 */
public abstract class BasicTripOfferData implements BasicTripOffer {
    protected TransPoolDriver transpoolDriver;
    protected int PPK;
    protected int maxPassengerCapacity;

    protected int ID;
    protected int tripPrice;
    protected double averageFuelConsumption;
    protected int tripDurationInMinutes;
    protected Recurrence recurrence;

    public BasicTripOfferData(String driverName, int PPK, int maxPassengerCapacity) {
        this.transpoolDriver = new TransPoolDriver(driverName);
        this.PPK = PPK;
        this.maxPassengerCapacity = maxPassengerCapacity;
    }

    public BasicTripOfferData(BasicTripOffer other) {
        this.transpoolDriver = other.getTransPoolDriver();
        this.PPK = other.getPPK();
        this.maxPassengerCapacity = other.getMaxPassengerCapacity();

    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public TransPoolDriver getTransPoolDriver() {
        return transpoolDriver;
    }

    @Override
    public int getPPK() {
        return PPK;
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
    public Recurrence getRecurrences() {
        return recurrence;
    }
}
