package data.transpool.trip.request.component;

import data.transpool.time.component.TimeDay;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public interface TripRequest extends BasicTripRequest {
    TripRequestDTO getDetails();
    TimeDay getRequestTime();
    TimeDay requestTimeProperty();
    boolean isTimeOfArrival();
    boolean isContinuous();

}
