package data.transpool.trip.offer.component;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeEngineBase;
import data.transpool.time.component.Scheduling;
import data.transpool.time.component.TimeDay;
import data.transpool.time.component.Recurrence;
import data.transpool.trip.request.component.BasicTripRequest;
import exception.data.RideFullException;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contains the data of a part of a trip offer (made from a single path)
 * dayToDetailsMap - contains the details of the matched trips on the relevant days.
 */
public class SubTripOffer extends BasicTripOfferData {
    private TripOffer mainOffer;
    private int subTripOfferID;

    private Stop sourceStop;
    private Stop destinationStop;

    private Map<Integer, SubTripOfferDetails> dayToDetailsMap;

    public SubTripOffer(int ID, Path path, TripOffer tripOffer) {
        super(tripOffer);
        this.mainOffer = tripOffer;
        this.subTripOfferID = ID;
        this.sourceStop = path.getSourceStop();
        this.destinationStop = path.getDestinationStop();

        this.tripPrice = path.getLength() * PPK;
        this.averageFuelConsumption = path.getFuelConsumption();
        this.tripDurationInMinutes = path.getPathTime();
        this.dayToDetailsMap = new HashMap<>();
        this.schedule = 
                new Scheduling(
                        tripOffer.getDepartureTimeAtStop(sourceStop),
                        tripOffer.getDepartureTimeAtStop(destinationStop),
                        tripOffer.getScheduling().getRecurrences()
                );
    }


    public int getSubTripOfferID() {
        return subTripOfferID;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }


    /**
     * Adds the relevant details on day 'day'.
     *
     * @param day         - The day to add the details.
     * @param matchedTrip - The matchedTrip to create the details from.
     * @throws RideFullException - Thrown if the ride is full and you can't add a rider on that day.
     */
    public void addRiderOnDay(int day, BasicTripRequest matchedTrip) throws RideFullException {
        if (dayToDetailsMap.get(day) != null) {
            dayToDetailsMap.get(day).addRider(matchedTrip);
        } else {
            dayToDetailsMap.put(day, new SubTripOfferDetails(this, matchedTrip));
        }
    }

    public TimeDay getTimeOfArrivalAtDestination() {
        return schedule.getDepartureTime();
    }

    public TimeDay getTimeOfDepartureFromSource() {
        return schedule.getDepartureTime();
    }

    public int getDayStart() {
        return schedule.getDayStart();
    }

    public Recurrence getRecurrences() {
        return schedule.getRecurrences();
    }

    public TripOffer getMainOffer() {
        return mainOffer;
    }

    public boolean isCurrentlyHappening() {
        return schedule.isCurrentlyHappening();
    }

    public boolean isCurrentlyDeparting() {
        return schedule.isCurrentlyDeparting();
    }

    public boolean isCurrentlyArriving() {
        return schedule.isCurrentlyArriving();
    }

    public Scheduling getFirstRecurrenceAfter(TimeDay timeDay) {
        return Scheduling.getFirstRecurrenceAfter(getScheduling(), timeDay);
    }


    /**
     * @return - a string of the details happening currently. Uses TransPoolData.currentTime.
     * Used for displaying who is staying at which station currently.
     */
    public String currentDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Driver: ").append(getTransPoolDriver().toString()).append("\n");
        if (dayToDetailsMap.get(TimeEngineBase.currentTime.getDay()) != null) {
            builder.append(dayToDetailsMap.get(TimeEngineBase.currentTime.getDay()));
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
        if (!(o instanceof SubTripOffer)) return false;
        SubTripOffer that = (SubTripOffer) o;
        return subTripOfferID == that.subTripOfferID &&
                sourceStop.equals(that.sourceStop) &&
                destinationStop.equals(that.destinationStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTripOfferID, sourceStop, destinationStop);
    }
}
