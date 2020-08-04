package api.transpool.time.component;

import exception.parser.TransPoolDataException;

import java.time.LocalTime;
import java.util.Objects;

/**
 * A class for managing the time and day. Not only live time and day, but also some timestamp. (what?)
 */

public class TimeDay {

    private static final int DAY_START = 1;
    private static final LocalTime TIME_START = LocalTime.MIDNIGHT;

    private LocalTime time;
    private int day;

    public TimeDay() {
        time = TIME_START;
        day = DAY_START;
    }

    public TimeDay(LocalTime time, int day) throws TransPoolDataException {
        this.time = time;
        setDay(day);
    }

    public TimeDay(TimeDay other) {
        this.day = other.day;
        this.time = other.time;
    }

    public void setDay(Integer day) {
        if (day == null) {
            this.day = 1;
        } else {
            this.day = day;
        }
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }
    
    /**
     * Checks to see if an instance of TimeDay is before another instance ofTimeDay.
     * @param other - the other TimeDay (duh)
     * @return - true if this instance is before other, false otherwise.
     */
    public boolean isBefore(TimeDay other) {
        if (this.day < other.day) {
            return true;
        } else if (this.day == other.day) {
            return this.time.isBefore(other.time);
        } else {
            return false;
        }
    }


    /**
     * Checks to see if an instance of TimeDay is after another instance ofTimeDay.
     * @param other - the other TimeDay.
     * @return - true if this instance is after other, false otherwise.
     */
    public boolean isAfter(TimeDay other) {
        if (this.day > other.day) {
            return true;
        } else if (this.day == other.day) {
            return this.time.isAfter(other.time);
        } else {
            return false;
        }
    }

    /**
     * Add minutes to this instance.
     * @param minutes - to add.
     */
    public void plus(int minutes) {
        LocalTime timeBefore = time;
        time = time.plusMinutes(minutes);
        if (time.compareTo(timeBefore) <= 0) {
            day = day + 1;
        }
    }


    /**
     * Reduces minutes from this instance.
     * @param minutes - minutes to reduce.
     */
    public void minus(int minutes) {
        LocalTime timeBefore = time;
        LocalTime timeAfter = time.minusMinutes(minutes);
        int dayAfter = day;
        if (timeAfter.compareTo(timeBefore) >= 0) {
            dayAfter--;
        }
        if (dayAfter >= DAY_START && !timeAfter.isBefore(TIME_START)) {
            time = timeAfter;
            day = dayAfter;
        }
    }

    public void setNextRecurrence(Recurrence recurrences) {
        day += recurrences.getValue();
    }

    public boolean isInRange(TimeDay time1, TimeDay time2) {
        return !this.isAfter(time2) && !this.isBefore(time1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeDay)) return false;
        TimeDay timeDay = (TimeDay) o;
        return time.equals(timeDay.time) &&
                day == timeDay.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }

    @Override
    public String toString() {
        return "Day " + day + " at " + time;
    }

}
