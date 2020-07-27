package data.transpool.trip.request.component;

import data.transpool.map.component.Stop;
import data.transpool.user.account.TransPoolRider;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface BasicTripRequest {
    TransPoolRider getTransPoolRider();
    void setTransPoolRider(TransPoolRider transpoolRider);

    int getRequestID();

    Stop getSourceStop();
    void setSourceStop(Stop sourceStop);

    Stop getDestinationStop();
    void setDestinationStop(Stop destinationStop);
}
