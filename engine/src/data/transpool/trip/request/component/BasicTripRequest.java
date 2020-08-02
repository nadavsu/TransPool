package data.transpool.trip.request.component;

import data.transpool.SingleMapEngine;
import data.transpool.map.component.Stop;
import data.transpool.user.account.TransPoolRider;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface BasicTripRequest {

    TransPoolRider getTransPoolRider();

    int getRequestID();

    Stop getSourceStop();

    Stop getDestinationStop();

    boolean isBelongToMap(SingleMapEngine map);

}

