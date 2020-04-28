package data.transpool.trips;

import exceptions.data.time.InvalidTimeException;

public class Scheduling {
    private enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

/*    private enum Recurrences {
        DAILY, WEEKLY, MONTHLY
    }*/

    private Day day;
    private Time timeOfDeparture;
    private String recurrences;

    public Scheduling(Day day, Time timeOfDeparture, String recurrences) {
        this.day = day;
        this.timeOfDeparture = timeOfDeparture;
        this.recurrences = recurrences;
    }

    public Scheduling(data.jaxb.Scheduling scheduling) throws InvalidTimeException {
        day = Day.values()[scheduling.getDayStart() - 1];
        recurrences = scheduling.getRecurrences();
        timeOfDeparture = new Time(scheduling.getHourStart(), 0);
    }

    public Time getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public void setTimeOfDeparture(Time timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    @Override
    public String toString() {
        return "Day: " + day +
                "\nTime of departure: " + timeOfDeparture +
                "\nRecurrences: " + recurrences;
    }
}
