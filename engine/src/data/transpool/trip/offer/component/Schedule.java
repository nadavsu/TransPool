package data.transpool.trip.offer.component;

import data.transpool.time.component.Recurrence;
import data.transpool.time.component.TimeDay;

public interface Schedule {
    Recurrence getOccurrenceType();
    Occurrence getFirstOccurrenceAfter(TimeDay timeDay);
    Occurrence getNextOccurrence();
    void setNextOccurrence();
}
