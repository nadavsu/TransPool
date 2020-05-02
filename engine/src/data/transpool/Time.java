package data.transpool;

import exceptions.data.time.InvalidHoursException;
import exceptions.data.time.InvalidMinutesException;
import exceptions.data.time.InvalidTimeException;

import java.util.Calendar;

public class Time {

    private Calendar calendar;

    public Time(int hour, int min) throws InvalidTimeException {
        setHour(hour);
        setMin(min);
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR);
    }

    public void setHour(int hour) throws InvalidTimeException {
        if (hour < 0 || hour > 23) {
            throw new InvalidHoursException();
        }
        calendar.set(Calendar.HOUR, hour);
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
        return calendar.toString();
    }
}
