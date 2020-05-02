package data.transpool;

import exceptions.data.time.InvalidTimeException;

public class Scheduling {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    public enum Recurrences {
        DAILY, BIDAILY, WEEKLY, MONTHLY
    }

    private Day day;
    private Recurrences recurrences;
    private Time time;

    public Scheduling(data.jaxb.Scheduling JAXBScheduling) throws InvalidTimeException {
        day = Day.MONDAY;
        recurrences = Recurrences.DAILY;
        time = new Time(JAXBScheduling.getHourStart(), 0);
    }

    public Day getDay() {
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
