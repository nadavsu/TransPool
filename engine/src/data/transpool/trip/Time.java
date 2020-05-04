package data.transpool.trip;

import exception.time.InvalidHoursException;
import exception.time.InvalidMinutesException;
import exception.time.InvalidTimeException;

import java.util.Calendar;
import java.util.Objects;

public class Time {

    private Calendar calendar = Calendar.getInstance();

    public Time(int hour, int min) throws InvalidTimeException {
        setHour(hour);
        setMin(0);
    }

    public Time(Time other) {
        calendar.set(Calendar.HOUR_OF_DAY, other.getHour());
        calendar.set(Calendar.MINUTE, other.getMin());
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public void setHour(int hour) throws InvalidTimeException {
        if (hour < 0 || hour > 23) {
            throw new InvalidHoursException();
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    public int getMin() {
        return calendar.get(Calendar.MINUTE);
    }

    public void setMin(int min) throws InvalidMinutesException {
        if (min < 0 || min > 59) {
            throw new InvalidMinutesException();
        }
        calendar.set(Calendar.MINUTE, min);
    }

    public void addMinutes(int minutes) {
        calendar.add(Calendar.MINUTE, minutes);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(calendar.get(Calendar.HOUR_OF_DAY)).append(":");
        if (calendar.get(Calendar.MINUTE) < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(calendar.get(Calendar.MINUTE));
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;
        Time time = (Time) o;
        return time.calendar.get(Calendar.HOUR_OF_DAY) == this.calendar.get(Calendar.HOUR_OF_DAY)
                && time.calendar.get(Calendar.MINUTE) == this.calendar.get(Calendar.MINUTE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendar);
    }
}
