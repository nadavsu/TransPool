package api.components.cards.request;

import javafx.beans.property.*;

import java.time.LocalTime;

public class BasicTripRequestData implements TripRequestData {
    protected StringProperty riderName;
    protected IntegerProperty requestID;
    protected StringProperty sourceStop;
    protected StringProperty destinationStop;
    protected BooleanProperty isTimeOfArrival;
    protected SimpleObjectProperty<LocalTime> requestTime;
    protected SimpleBooleanProperty isContinuous;

    public BasicTripRequestData(int requestID, String riderName, String sourceStop, String destinationStop,
                                LocalTime requestTime, boolean isTimeOfArrival, boolean isContinuous) {
        this.riderName = new SimpleStringProperty(riderName);
        this.requestID = new SimpleIntegerProperty(requestID);
        this.sourceStop = new SimpleStringProperty(sourceStop);
        this.destinationStop = new SimpleStringProperty(destinationStop);
        this.isTimeOfArrival = new SimpleBooleanProperty(isTimeOfArrival);
        this.requestTime = new SimpleObjectProperty<>(requestTime);
        this.isContinuous = new SimpleBooleanProperty(isContinuous);
    }

    @Override
    public String getRiderName() {
        return this.riderName.get();
    }

    @Override
    public void setRiderName(String riderName) {
        this.riderName.set(riderName);
    }

    @Override
    public int getRequestID() {
        return this.requestID.get();
    }

    @Override
    public void setRequestID(int requestID) {
        this.requestID.set(requestID);
    }

    @Override
    public String getSourceStop() {
        return this.sourceStop.get();
    }

    @Override
    public void setSourceStop(String sourceStop) {
        this.sourceStop.set(sourceStop);
    }

    @Override
    public String getDestinationStop() {
        return this.destinationStop.get();
    }

    @Override
    public void setDestinationStop(String destionationStop) {
        this.destinationStop.set(destionationStop);
    }

    @Override
    public boolean isTimeOfArrival() {
        return this.isTimeOfArrival.get();
    }

    @Override
    public void setTimeOfArrival(boolean value) {
        this.isTimeOfArrival.set(value);
    }

    @Override
    public LocalTime getRequestTime() {
        return this.requestTime.get();
    }

    @Override
    public void setRequestTime(LocalTime requestTime) {
        this.requestTime.set(requestTime);
    }

    @Override
    public boolean isContinuous() {
        return false;
    }

    @Override
    public void setContinuous(boolean value) {

    }
}
