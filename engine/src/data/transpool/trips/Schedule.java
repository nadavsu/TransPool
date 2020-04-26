package data.transpool.trips;

import data.generated.Scheduling;

public class Schedule {
    private enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

/*    private enum Recurrences {
        DAILY, WEEKLY, MONTHLY
    }*/

    private Day day;
    private Time timeOfDeparture;
    private String recurrences;

    public Schedule(Day day, Time timeOfDeparture, String recurrences) {
        this.day = day;
        this.timeOfDeparture = timeOfDeparture;
        this.recurrences = recurrences;
    }

    public Schedule(Scheduling scheduling) {
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
}
