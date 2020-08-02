package data.transpool.trip.request.component;

import data.transpool.SingleMapEngine;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class TripRequestData extends BasicTripRequestData implements TripRequest {

    private TimeDay requestTime;
    private boolean isTimeOfArrival;
    private boolean isContinuous;

    public TripRequestData(BasicMap map, String riderName, String sourceStop, String destinationStop, int day,
                           LocalTime requestTime, boolean isTimeOfArrival, boolean isContinuous) throws TransPoolDataException {
        super(map, riderName, sourceStop, destinationStop);
        this.requestTime = new TimeDay(requestTime, day);
        this.isTimeOfArrival = isTimeOfArrival;
        this.isContinuous = isContinuous;
        setDay(day);
    }

    @Override
    public TripRequestDTO getDetails() {
        return new TripRequestDTO(this);
    }

    @Override
    public boolean isTimeOfArrival() {
        return isTimeOfArrival;
    }

    @Override
    public TimeDay getRequestTime() {
        return requestTime;
    }

    private void setDay(int day) throws InvalidDayStartException {
        requestTime.setDay(day);
    }

    @Override
    public boolean isContinuous() {
        return isContinuous;
    }

    @Override
    public String toString() {
        return transpoolRider.getUsername() + " - " + transpoolRider.getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripRequestData)) return false;
        TripRequestData that = (TripRequestData) o;
        return requestID == that.requestID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }
}
