package data.transpool.trip.offer.component;

import data.transpool.time.component.Scheduling;
import data.transpool.user.account.TransPoolDriver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface BasicTripOffer {
    int getOfferID();

    TransPoolDriver getTransPoolDriver();
    void setTransPoolDriver(TransPoolDriver transpoolDriver);

    Scheduling getScheduling();
    void setScheduling(Scheduling schedule);

    int getPPK();
    void setPPK(int PPK);

    int getTripDurationInMinutes();

    int getPrice();

    double getAverageFuelConsumption();

    int getMaxPassengerCapacity();
    void setMaxPassengerCapacity(int maxPassengerCapacity);






}
