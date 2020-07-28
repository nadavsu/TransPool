package data.transpool.trip.offer.component;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeEngineBase;
import data.transpool.time.component.Schedule;
import data.transpool.time.component.TimeDay;
import data.transpool.time.component.Recurrence;
import data.transpool.user.account.TransPoolDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contains the data of a part of a trip offer (made from a single path)
 * dayToDetailsMap - contains the details of the matched trips on the relevant days.
 */
public class TripOfferPart implements Schedule, BasicTripOffer {
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

    private Map<Integer, TripOfferPartOccurrence> dayToOccurrenceMap;

    public TripOfferPart(int ID, Path path, TripOffer tripOffer) {
        this.dayToOccurrenceMap = new HashMap<>();
        this.mainOffer = tripOffer;
        this.ID = ID;
        this.transpoolDriver = tripOffer.getTransPoolDriver();

        this.sourceStop = path.getSourceStop();
        this.destinationStop = path.getDestinationStop();

        this.PPK = tripOffer.getPPK();
        this.maxPassengerCapacity = tripOffer.getMaxPassengerCapacity();
        this.tripPrice = path.getLength() * tripOffer.getPPK();
        this.averageFuelConsumption = path.getFuelConsumption();
        this.tripDurationInMinutes = path.getPathTime();

        this.departureTime = tripOffer.getTimeAtStop(sourceStop);
        this.arrivalTime = tripOffer.getTimeAtStop(destinationStop);
        this.occurrenceType = tripOffer.getRecurrences();
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
    public int getDayStart() {
        return departureTime.getDay();
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
    public TripOfferPartOccurrence getFirstOccurrence() {
        return this.getOrCreateOccurrence(getDayStart());
    }

    @Override
    public TripOfferPartOccurrence getOccurrenceAfter(TimeDay timeDay) {
        if (!departureTime.isBefore(timeDay)) {
            return getFirstOccurrence();

        } else if (!occurrenceType.equals(Recurrence.ONE_TIME)) {
            TripOfferPartOccurrence currentOccurrence = getFirstOccurrence();
            int currentDay = currentOccurrence.getOccurrenceDay();

            while (currentOccurrence.isBefore(timeDay)) {
                currentDay += occurrenceType.getValue();
                currentOccurrence = getOrCreateOccurrence(currentDay);
            }

            return currentOccurrence;

        } else {
            return null;
        }
    }

    @Override
    public TripOfferPartOccurrence getOrCreateOccurrence(int occurrenceDay) {
        if (dayToOccurrenceMap.get(occurrenceDay) == null) {
            dayToOccurrenceMap.put(occurrenceDay, new TripOfferPartOccurrence(this, departureTime, arrivalTime, occurrenceDay));
        }
        return dayToOccurrenceMap.get(occurrenceDay);
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }

    public TripOffer getMainOffer() {
        return mainOffer;
    }

    public boolean isCurrentlyHappening() {
        return !TimeEngineBase.currentTime.getTime().isBefore(departureTime.getTime())
                && !TimeEngineBase.currentTime.getTime().isAfter(arrivalTime.getTime())
                && occurrenceType.isOnDay(getDayStart(), TimeEngineBase.currentTime.getDay());
    }

    public boolean isCurrentlyDeparting() {
        return TimeEngineBase.currentTime.getTime().equals(departureTime.getTime())
                && occurrenceType.isOnDay(getDayStart(), TimeEngineBase.currentTime.getDay());
    }

    public boolean isCurrentlyArriving() {
        return TimeEngineBase.currentTime.getTime().equals(arrivalTime.getTime())
                && occurrenceType.isOnDay(getDayStart(), TimeEngineBase.currentTime.getDay());
    }


    /**
     * @return - a string of the details happening currently. Uses TransPoolData.currentTime.
     * Used for displaying who is staying at which station currently.
     */
    public String currentDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Driver: ").append(getTransPoolDriver().toString()).append("\n");
        if (dayToOccurrenceMap.get(TimeEngineBase.currentTime.getDay()) != null) {
            builder.append(dayToOccurrenceMap.get(TimeEngineBase.currentTime.getDay()));
            builder.append("\n\n");
        } else {
            builder.append("Riding alone.\n\n");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Depart from " + getSourceStop() +
                " with " + getTransPoolDriver() +
                " and arrive at " + getDestinationStop();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripOfferPart)) return false;
        TripOfferPart that = (TripOfferPart) o;
        return this.ID == that.ID &&
                sourceStop.equals(that.sourceStop) &&
                destinationStop.equals(that.destinationStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, sourceStop, destinationStop);
    }
}
