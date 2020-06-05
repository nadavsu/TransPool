package data.transpool.trip.request;

import data.transpool.trip.TripRequestData;
import data.transpool.user.TransPoolRider;
import javafx.beans.property.*;

import java.time.LocalTime;

public abstract class BasicTripRequestData implements TripRequestData {

    private static int IDGenerator = 20000;
    protected IntegerProperty requestID;
    protected ObjectProperty<TransPoolRider> transpoolRider;
    protected StringProperty sourceStop;
    protected StringProperty destinationStop;

    public BasicTripRequestData(String riderName, String sourceStop, String destinationStop) {
        this.requestID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolRider = new SimpleObjectProperty<>(new TransPoolRider(riderName));
        this.sourceStop = new SimpleStringProperty(sourceStop);
        this.destinationStop = new SimpleStringProperty(destinationStop);
    }

    @Override
    public String getRiderName() {
        return null;
    }

    @Override
    public void setRiderName(String riderName) {

    }

    @Override
    public int getRequestID() {
        return 0;
    }

    @Override
    public void setRequestID(int requestID) {

    }

    @Override
    public String getSourceStop() {
        return null;
    }

    @Override
    public void setSourceStop(String sourceStop) {

    }

    @Override
    public String getDestinationStop() {
        return null;
    }

    @Override
    public void setDestinationStop(String destinatinStop) {

    }

    @Override
    public boolean isTimeOfArrival() {
        return false;
    }

    @Override
    public void setTimeOfArrival(boolean value) {

    }

    @Override
    public LocalTime getRequestTime() {
        return null;
    }

    @Override
    public void setRequestTime(LocalTime requestTime) {

    }

    @Override
    public boolean isContinuous() {
        return false;
    }

    @Override
    public void setContinuous(boolean value) {

    }
}
