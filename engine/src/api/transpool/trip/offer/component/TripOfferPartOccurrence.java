package api.transpool.trip.offer.component;

import api.transpool.SingleMapEngine;
import api.transpool.map.component.Stop;
import api.transpool.time.TimeEngineBase;
import api.transpool.time.component.Occurrence;
import api.transpool.time.component.Recurrence;
import api.transpool.time.component.TimeDay;
import api.transpool.user.account.Rider;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.account.TransPoolRider;
import exception.parser.RideFullException;

import java.util.ArrayList;
import java.util.List;

/**
 * A single occurrence of a trip offer part. Each trip offer part occurrence can occur on different days, and have
 * different riders. These objects are used for creating the route, and for storing the the information of a trip
 * on a single day, rather than the static trip offer.
 */

public class TripOfferPartOccurrence implements Occurrence, BasicTripOffer {
    private TripOffer mainOffer;

    private int ID;
    private TransPoolDriver transpoolDriver;
    private int PPK;
    private int maxPassengerCapacity;
    private int tripPrice;
    private double averageFuelConsumption;
    private int tripDurationInMinutes;

    private Stop sourceStop;
    private Stop destinationStop;

    private TimeDay departureTime;
    private TimeDay arrivalTime;
    private Recurrence occurrenceType;

    private int spacesLeft;
    private List<Rider> riders;

    public TripOfferPartOccurrence(TripOfferPart tripOfferPart, TimeDay departureTime, TimeDay arrivalTime, int day) {
        this.mainOffer = tripOfferPart.getMainOffer();
        this.riders = new ArrayList<>();
        this.spacesLeft = tripOfferPart.getMaxPassengerCapacity();

        this.departureTime = new TimeDay(departureTime);
        this.arrivalTime = new TimeDay(arrivalTime);
        setDay(day);

        this.ID = tripOfferPart.getID();
        this.transpoolDriver = tripOfferPart.getTransPoolDriver();
        this.PPK = tripOfferPart.getPPK();
        this.maxPassengerCapacity = tripOfferPart.getMaxPassengerCapacity();
        this.tripPrice = tripOfferPart.getPrice();
        this.averageFuelConsumption = tripOfferPart.getAverageFuelConsumption();
        this.tripDurationInMinutes = tripOfferPart.getTripDurationInMinutes();

        this.sourceStop = tripOfferPart.getSourceStop();
        this.destinationStop = tripOfferPart.getDestinationStop();
        this.occurrenceType = tripOfferPart.getRecurrences();
    }

    private void setDay(int day) {
        departureTime.setDay(day);
        arrivalTime.setDay(day);
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
    public int getMaxPassengerCapacity() {
        return maxPassengerCapacity;
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
    public int getTripDurationInMinutes() {
        return tripDurationInMinutes;
    }

    @Override
    public Stop getSourceStop() {
        return sourceStop;
    }

    @Override
    public Stop getDestinationStop() {
        return destinationStop;
    }

    @Override
    public TimeDay getDepartureTime() {
        return departureTime;
    }

    @Override
    public TimeDay getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public Recurrence getRecurrences() {
        return occurrenceType;
    }

    @Override
    public boolean isBelongToMap(SingleMapEngine map) {
        return map.getTripOfferPart(mainOffer.getID(), ID) != null;
    }

    @Override
    public TimeDay getOccurrenceStart() {
        return departureTime;
    }

    @Override
    public TimeDay getOccurrenceEnd() {
        return arrivalTime;
    }

    @Override
    public int getOccurrenceDay() {
        return departureTime.getDay();
    }

    @Override
    public boolean isAfter(TimeDay timeDay) {
        return departureTime.isAfter(timeDay);
    }

    @Override
    public boolean isAfter(Occurrence other) {
        return !this.getOccurrenceStart().isBefore(other.getOccurrenceEnd());
    }

    @Override
    public boolean isBefore(TimeDay timeDay) {
        return arrivalTime.isBefore(timeDay);
    }

    @Override
    public boolean isBefore(Occurrence other) {
        return !this.getOccurrenceEnd().isAfter(other.getOccurrenceStart());
    }

    @Override
    public boolean isOccurring() {
        return TimeEngineBase.currentTime.isInRange(departureTime, arrivalTime);
    }

    @Override
    public boolean isStarting() {
        return TimeEngineBase.currentTime.getTime().equals(departureTime.getTime())
                && departureTime.getDay() == TimeEngineBase.currentTime.getDay();
    }

    @Override
    public boolean isEnding() {
        return TimeEngineBase.currentTime.getTime().equals(arrivalTime.getTime())
                && arrivalTime.getDay() == TimeEngineBase.currentTime.getDay();
    }

    public int getSpacesLeft() {
        return spacesLeft;
    }

    public List<Rider> getRiders() {
        return riders;
    }

    public void addRider(Rider rider) throws RideFullException {
        if (spacesLeft > 0) {
            riders.add(rider);
            spacesLeft--;
        } else {
            throw new RideFullException();
        }
    }

    public void updateFather(TransPoolRider transPoolRider) {
        mainOffer.updateAfterMatch(transPoolRider, this);
    }

    public String getTripOfferOccurrenceDescription() {
        return "Depart from " + sourceStop + " on " + departureTime + " with " + transpoolDriver
                + " and arrive on " + arrivalTime + " at " + destinationStop;
    }
}
