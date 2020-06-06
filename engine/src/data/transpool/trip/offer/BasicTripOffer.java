package data.transpool.trip.offer;

import data.transpool.trip.Route;
import data.transpool.trip.Scheduling;
import data.transpool.user.TransPoolDriver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface BasicTripOffer {
    int getOfferID();
    IntegerProperty offerIDProperty();

    TransPoolDriver getTransPoolDriver();
    void setTransPoolDriver(TransPoolDriver transpoolDriver);
    ObjectProperty<TransPoolDriver> transpoolDriverProperty();

    Route getRoute();
    void setRoute(Route route);
    ObjectProperty<Route> routeProperty();

    Scheduling getScheduling();
    void setScheduling(Scheduling schedule);
    ObjectProperty<Scheduling> schedulingProperty();

    int getPPK();
    void setPPK(int PPK);
    IntegerProperty PPKProperty();

    int getTripDurationInMinutes();
    IntegerProperty tripDurationInMinutesProperty();

    int getPrice();
    IntegerProperty priceProperty();

    double getAverageFuelConsumption();
    DoubleProperty averageFuelConsumptionProperty();

    int calculatePriceOfRoute(Route route, int PPK);
    double calculateAverageFuelConsumption(Route route);
    int calculateTripDuration(Route route);
    void initialize();






}
