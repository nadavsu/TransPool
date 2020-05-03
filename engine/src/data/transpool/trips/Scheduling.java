package data.transpool.trips;

import exceptions.time.InvalidTimeException;

public class Scheduling {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    public enum Recurrences {
        DAILY, BIDAILY, WEEKLY, MONTHLY
    }

    private int day;
    private Recurrences recurrences;
    private Time time;

    public Scheduling(data.jaxb.Scheduling JAXBScheduling) throws InvalidTimeException {
        day = 0;
        recurrences = Recurrences.DAILY;
        time = new Time(JAXBScheduling.getHourStart(), 0);
    }

    public int getDay() {
        return day;
    }

    public Recurrences getRecurrences() {
        return recurrences;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return recurrences + " at days " + day + " at time " + time;
    }
}
