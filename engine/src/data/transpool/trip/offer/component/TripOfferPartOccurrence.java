package data.transpool.trip.offer.component;

import data.transpool.time.TimeEngineBase;
import data.transpool.time.component.Recurrence;
import data.transpool.time.component.TimeDay;

public class TripOfferPartOccurrence implements Occurrence {

    private TimeDay departureTime;
    private TimeDay arrivalTime;

    public TripOfferPartOccurrence(TripOfferPart tripOfferPart, TimeDay departureTime, TimeDay arrivalTime) {

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
}
