package data.transpool.trips;

import exceptions.data.time.InvalidTimeException;

public class Scheduling {
/*
    private enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }
*/

/*    private enum Recurrences {
        DAILY, WEEKLY, MONTHLY
    }*/

    private int day;
    private Time timeOfDeparture;
    private String recurrences;

    public Scheduling(int day, Time timeOfDeparture, String recurrences) {
        //this.day = day;
        //this.recurrences = recurrences;
        this.timeOfDeparture = timeOfDeparture;
    }

    public Scheduling(data.jaxb.Scheduling scheduling) throws InvalidTimeException {
        //day = scheduling.getDayStart();
        //recurrences = scheduling.getRecurrences();
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
        return  "Time of departure: " + timeOfDeparture;
    }
}
