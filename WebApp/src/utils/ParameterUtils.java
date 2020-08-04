package utils;

import api.transpool.time.component.Recurrence;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class ParameterUtils {

    public static LocalTime getTimeFromParameter(String timeParameter) {
        String[] time = timeParameter.split(":");
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        return LocalTime.of(hour, min);
    }

    public static List<String> getRouteFromParameter(String route) {
        return Arrays.asList(route.split(","));
    }

    public static Recurrence getRecurrenceFromParameter(String recurrence) {
        return Recurrence.getRecurrence(recurrence);
    }

    public static Boolean getBooleanParameter(String parameter) {
        return parameter != null;
    }
}
