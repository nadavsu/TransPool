package data.transpool.time.component;

public interface Schedule {
    Recurrence getRecurrences();
    Occurrence getFirstOccurrence();
    Occurrence getOccurrenceAfter(TimeDay timeDay);
    Occurrence getOrCreateOccurrence(int occurrenceDay);

    int getDayStart();
    TimeDay getDepartureTime();
    TimeDay getArrivalTime();
}
