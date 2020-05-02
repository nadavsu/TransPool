package data.transpool;

import java.util.Calendar;

public interface TripRequest {
    int getID();

    String getRequesterName();

    String getSource();

    String getDestination();

    Time getTimeOfDeparture();

    boolean isContinuous();

}
