package data.transpool.trip.offer.component;

import data.transpool.time.component.Recurrence;
import data.transpool.time.component.TimeDay;

public interface Occurrence {
    TimeDay getOccurrenceStart();
    TimeDay getOccurrenceEnd();
    boolean isAfter(Occurrence other);
    boolean isAfter(TimeDay timeDay);
    boolean isBefore(Occurrence other);
    boolean isBefore(TimeDay timeDay);


    boolean isOccurring();
    boolean isStarting();
    boolean isEnding();

}
