package data.transpool.trip.request.component;

import data.transpool.map.component.Stop;
import data.transpool.user.account.TransPoolRider;
import javafx.beans.property.*;

import java.util.Objects;

/**
 * A class holding the basic Trip Request data.
 */
public abstract class BasicTripRequestData implements BasicTripRequest {

    private static int IDGenerator = 20000;
    protected int requestID;
    protected TransPoolRider transpoolRider;
    protected Stop sourceStop;
    protected Stop destinationStop;

    public BasicTripRequestData(String riderName, Stop sourceStop, Stop destinationStop) {
        this.requestID = IDGenerator++;
        this.transpoolRider = new TransPoolRider(riderName);
        this.sourceStop = new Stop(sourceStop);
        this.destinationStop = new Stop(destinationStop);
    }

    public BasicTripRequestData(TripRequest other) {
        this.requestID = other.getRequestID();
        this.transpoolRider = new TransPoolRider(other.getTransPoolRider());
        this.sourceStop = new Stop(other.getSourceStop());
        this.destinationStop = new Stop(other.getDestinationStop());
    }

    @Override
    public int getRequestID() {
        return this.requestID;
    }

    @Override
    public TransPoolRider getTransPoolRider() {
        return transpoolRider;
    }

    @Override
    public void setTransPoolRider(TransPoolRider transpoolRider) {
        this.transpoolRider = transpoolRider;
    }


    @Override
    public Stop getSourceStop() {
        return this.sourceStop;
    }

    @Override
    public void setSourceStop(Stop sourceStop) {
        this.sourceStop = sourceStop;
    }

    @Override
    public Stop getDestinationStop() {
        return this.destinationStop;
    }

    @Override
    public void setDestinationStop(Stop destinationStop) {
        this.destinationStop = destinationStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicTripRequestData)) return false;
        BasicTripRequestData that = (BasicTripRequestData) o;
        return requestID == (that.requestID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }

    @Override
    public String toString() {
        return "Rider " + requestID + " - " + getTransPoolRider();
    }
}
