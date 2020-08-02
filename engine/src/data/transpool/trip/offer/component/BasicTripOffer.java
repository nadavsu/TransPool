package data.transpool.trip.offer.component;

import data.transpool.SingleMapEngine;
import data.transpool.map.component.Stop;
import data.transpool.time.component.Recurrence;
import data.transpool.time.component.TimeDay;
import data.transpool.user.account.TransPoolDriver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

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
