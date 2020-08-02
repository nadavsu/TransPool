package data.transpool.time.component;

public interface Occurrence {
    TimeDay getOccurrenceStart();
    TimeDay getOccurrenceEnd();
    int getOccurrenceDay();
    boolean isAfter(Occurrence other);
    boolean isAfter(TimeDay timeDay);
    boolean isBefore(Occurrence other);
    boolean isBefore(TimeDay timeDay);

    boolean isOccurring();
    boolean isStarting();
    boolean isEnding();

}
