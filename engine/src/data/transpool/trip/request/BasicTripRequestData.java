package data.transpool.trip.request;

import data.transpool.user.TransPoolRider;
import javafx.beans.property.*;

import java.util.Objects;

public abstract class BasicTripRequestData implements BasicTripRequest {

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

    public BasicTripRequestData(TripRequest other) {
        this.requestID = new SimpleIntegerProperty(other.getRequestID());
        this.transpoolRider = new SimpleObjectProperty<>(new TransPoolRider(other.getTransPoolRider()));
        this.sourceStop = new SimpleStringProperty(other.getSourceStop());
        this.destinationStop = new SimpleStringProperty(other.getDestinationStop());
    }

    @Override
    public int getRequestID() {
        return this.requestID.get();
    }

    @Override
    public TransPoolRider getTransPoolRider() {
        return transpoolRider.get();
    }

    @Override
    public void setTransPoolRider(TransPoolRider transpoolRider) {
        this.transpoolRider.set(transpoolRider);
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
    public void setDestinationStop(String destinatinStop) {
        this.destinationStop.set(destinatinStop);
    }

    @Override
    public ObjectProperty<TransPoolRider> transpoolRiderProperty() {
        return transpoolRider;
    }

    @Override
    public IntegerProperty requestIDProperty() {
        return requestID;
    }

    @Override
    public StringProperty sourceStopProperty() {
        return sourceStop;
    }

    @Override
    public StringProperty destinationStopProperty() {
        return destinationStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicTripRequestData)) return false;
        BasicTripRequestData that = (BasicTripRequestData) o;
        return requestID.equals(that.requestID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }

    @Override
    public String toString() {
        return "Rider " + requestID.get()
                + " gets on at " + sourceStop.get()
                + " and gets off at " + destinationStop.get();
    }
}
