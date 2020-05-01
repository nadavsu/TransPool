package data.transpool;

import exceptions.data.time.InvalidHoursException;
import exceptions.data.time.InvalidMinutesException;
import exceptions.data.time.InvalidTimeException;

public class Time {
    private int hour;
    private int min;

    public Time(int hour, int min) throws InvalidTimeException {
        setHour(hour);
        setMin(min);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) throws InvalidTimeException {
        if (hour < 0 || hour > 23) {
            throw new InvalidHoursException();
        }
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) throws InvalidMinutesException {
        if (min < 0 || min > 59) {
            throw new InvalidMinutesException();
        }
        this.min = 0;
    }
}
