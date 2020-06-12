package data.transpool.time;

import data.transpool.trip.offer.graph.SubTripOffer;

import java.time.LocalTime;

public class TimeDay {
    private LocalTime time;
    private int day;

    public TimeDay(LocalTime time, int day) {
        this.time = time;
        this.day = day;
    }

    public TimeDay(TimeDay other) {
        this.day = other.day;
        this.time = other.time;
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

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isBefore(TimeDay other) {
        if (this.day < other.day) {
            return true;
        } else if (this.day == other.day) {
            return this.time.isBefore(other.time)
                    || this.time.equals(other.time);
        } else {
            return false;
        }
    }
}
