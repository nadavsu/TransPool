package data.transpool.time;

import data.transpool.trip.offer.graph.SubTripOffer;
import exception.TransPoolRunTimeException;
import exception.data.InvalidDayStartException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;
import java.util.Objects;

public class TimeDay {
    private ObjectProperty<LocalTime> time;
    private IntegerProperty day;

    public TimeDay(LocalTime time, int day) {
        this.time = new SimpleObjectProperty<>(time);
        this.day = new SimpleIntegerProperty(day);
    }

    public TimeDay(TimeDay other) {
        this.day = new SimpleIntegerProperty(other.day.get());
        this.time = new SimpleObjectProperty<>(other.time.get());
    }

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public int getDay() {
        return day.get();
    }

    public IntegerProperty dayProperty() {
        return day;
    }

    public void setDay(int day) throws InvalidDayStartException {
        if (day < 1) {
            throw new InvalidDayStartException();
        }
        this.day.set(day);
    }

    public boolean isBefore(TimeDay other) {
        if (this.day.get() < other.day.get()) {
            return true;
        } else if (this.day.get() == other.day.get()) {
            return this.time.get().isBefore(other.time.get())
                    || this.time.get().equals(other.time.get());
        } else {
            return false;
        }
    }
    
    public boolean isAfter(TimeDay other) {
        if (this.day.get() > other.day.get()) {
            return true;
        } else if (this.day.get() == other.day.get()) {
            return this.time.get().isAfter(other.time.get())
                    || this.time.get().equals(other.time.get());
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeDay)) return false;
        TimeDay timeDay = (TimeDay) o;
        return time.get().equals(timeDay.time.get()) &&
                day.get() == timeDay.day.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }

    public void setNextRecurrence(String recurrences) {
        if (recurrences.equals("Daily")) {
            day.set(day.get() + 1);
        } else if (recurrences.equals("Bi-daily")) {
            day.set(day.get() + 2);
        } else if (recurrences.equals("Weekly")) {
            day.set(day.get() + 7);
        } else if (recurrences.equals("Monthly")) {
            day.set(day.get() + 30);
        } else {
            throw new TransPoolRunTimeException();
        }
    }

    @Override
    public String toString() {
        return getTime() + " on day " + getDay();
    }


}
