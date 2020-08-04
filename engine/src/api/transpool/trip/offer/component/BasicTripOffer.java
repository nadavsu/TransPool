package api.transpool.trip.offer.component;

import api.transpool.SingleMapEngine;
import api.transpool.map.component.Stop;
import api.transpool.time.component.Recurrence;
import api.transpool.time.component.TimeDay;
import api.transpool.user.account.TransPoolDriver;

public interface BasicTripOffer {
    int getID();

    TransPoolDriver getTransPoolDriver();

    int getPPK();

    int getTripDurationInMinutes();

    int getPrice();

    double getAverageFuelConsumption();

    int getMaxPassengerCapacity();

    Stop getSourceStop();

    Stop getDestinationStop();

    TimeDay getDepartureTime();

    TimeDay getArrivalTime();

    Recurrence getRecurrences();

    boolean isBelongToMap(SingleMapEngine map);
}
