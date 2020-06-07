package data.transpool.trip.request;

import data.transpool.trip.Route;
import data.transpool.trip.Scheduling;
import data.transpool.user.TransPoolRider;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface BasicTripRequest {
    TransPoolRider getTransPoolRider();
    void setTransPoolRider(TransPoolRider transpoolRider);
    ObjectProperty<TransPoolRider> transpoolRiderProperty();

    int getRequestID();
    IntegerProperty requestIDProperty();

    String getSourceStop();
    void setSourceStop(String sourceStop);
    StringProperty sourceStopProperty();

    String getDestinationStop();
    void setDestinationStop(String destinatinStop);
    StringProperty destinationStopProperty();
}
