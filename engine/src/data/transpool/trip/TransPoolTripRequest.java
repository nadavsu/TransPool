package data.transpool.trip;

import data.transpool.user.TransPoolRider;
import exception.data.StopNotFoundException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class TransPoolTripRequest {
    private static int IDGenerator = 20000;
    protected IntegerProperty requestID;
    protected ObjectProperty<TransPoolRider> transpoolRider;
    protected StringProperty sourceStop;
    protected StringProperty destinationStop;
    protected BooleanProperty isTimeOfArrival;
    protected SimpleObjectProperty<LocalTime> requestTime;
    protected SimpleBooleanProperty isContinuous;

    public TransPoolTripRequest(String riderName, String sourceStop, String destinationStop,
                                LocalTime requestTime, boolean isTimeOfArrival, boolean isContinuous) throws StopNotFoundException {
        this.requestID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolRider = new SimpleObjectProperty<>(new TransPoolRider(riderName));
        this.sourceStop = new SimpleStringProperty(sourceStop);
        this.destinationStop = new SimpleStringProperty(destinationStop);
        this.isTimeOfArrival = new SimpleBooleanProperty(isTimeOfArrival);
        this.requestTime = new SimpleObjectProperty<>(requestTime);
        this.isContinuous = new SimpleBooleanProperty(isContinuous);
    }

    public int getRequestID() {
        return requestID.get();
    }

    public IntegerProperty requestIDProperty() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID.set(requestID);
    }

    public TransPoolRider getTransPoolRider() {
        return transpoolRider.get();
    }

    public ObjectProperty<TransPoolRider> transpoolRiderProperty() {
        return transpoolRider;
    }

    public void setTranspoolRider(TransPoolRider transpoolRider) {
        this.transpoolRider.set(transpoolRider);
    }

    public String getSourceStop() {
        return sourceStop.get();
    }

    public StringProperty sourceStopProperty() {
        return sourceStop;
    }

    public void setSourceStop(String sourceStop) {
        this.sourceStop.set(sourceStop);
    }

    public String getDestinationStop() {
        return destinationStop.get();
    }

    public StringProperty destinationStopProperty() {
        return destinationStop;
    }

    public void setDestinationStop(String destinationStop) {
        this.destinationStop.set(destinationStop);
    }

    public boolean isIsTimeOfArrival() {
        return isTimeOfArrival.get();
    }

    public BooleanProperty isTimeOfArrivalProperty() {
        return isTimeOfArrival;
    }

    public void setIsTimeOfArrival(boolean isTimeOfArrival) {
        this.isTimeOfArrival.set(isTimeOfArrival);
    }

    public LocalTime getRequestTime() {
        return requestTime.get();
    }

    public SimpleObjectProperty<LocalTime> requestTimeProperty() {
        return requestTime;
    }

    public void setRequestTime(LocalTime requestTime) {
        this.requestTime.set(requestTime);
    }

    public boolean isIsContinuous() {
        return isContinuous.get();
    }

    public SimpleBooleanProperty isContinuousProperty() {
        return isContinuous;
    }

    public void setIsContinuous(boolean isContinuous) {
        this.isContinuous.set(isContinuous);
    }

    public TransPoolRider getTranspoolRider() {
        return transpoolRider.get();
    }

    @Override
    public String toString() {
        return transpoolRider.get().getUsername() + " - " + transpoolRider.get().getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransPoolTripRequest)) return false;
        TransPoolTripRequest that = (TransPoolTripRequest) o;
        return requestID.get() == that.requestID.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID.get());
    }
}
