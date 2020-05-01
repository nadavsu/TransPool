package data.transpool.trips;

import exceptions.data.time.InvalidHoursException;
import exceptions.data.time.InvalidMinutesException;
import exceptions.data.time.InvalidTimeException;

public class Time {
    private int hour;
    private int min;

    public Time(int hour, int min) throws InvalidTimeException {
        setHour(hour);
        setMin(0);
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public void setHour(int hour) throws InvalidHoursException {
        if (hour < 0 || hour >= 24) {
            throw new InvalidHoursException();
        }
        this.hour = hour;
    }

    public void setMin(int min) throws InvalidMinutesException {
        if (min < 0 || min >= 60) {
            throw new InvalidMinutesException();
        }
        this.min = min;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (hour < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(hour + ":");
        if (min < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(min);
        return stringBuilder.toString();
    }
}
