package api.transpool.trip.request.component;

import api.transpool.SingleMapEngine;
import api.transpool.map.component.Stop;
import api.transpool.user.account.TransPoolRider;

public interface BasicTripRequest {

    TransPoolRider getTransPoolRider();

    int getID();

    Stop getSourceStop();

    Stop getDestinationStop();

    boolean isBelongToMap(SingleMapEngine map);

}

