package data.transpool.trip.request;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;

public interface TripRequest extends BasicTripRequest {
    boolean isTimeOfArrival();
    void setIsTimeOfArrival(boolean value);
    BooleanProperty isTimeOfArrivalProperty();

    LocalTime getRequestTime();
    SimpleObjectProperty<LocalTime> requestTimeProperty();
    void setRequestTime(LocalTime requestTime);

    boolean isContinuous();
    SimpleBooleanProperty isContinuousProperty();
    void setContinuous(boolean value);
}
