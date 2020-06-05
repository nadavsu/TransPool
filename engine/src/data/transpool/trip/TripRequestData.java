package data.transpool.trip;

import java.time.LocalTime;

public interface TripRequestData {
    String getRiderName();
    void setRiderName(String riderName);

    int getRequestID();
    void setRequestID(int requestID);

    String getSourceStop();
    void setSourceStop(String sourceStop);

    String getDestinationStop();
    void setDestinationStop(String destinatinStop);

    boolean isTimeOfArrival();
    void setTimeOfArrival(boolean value);

    LocalTime getRequestTime();
    void setRequestTime(LocalTime requestTime);

    boolean isContinuous();
    void setContinuous(boolean value);
}
