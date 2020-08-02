package data.transpool.trip.request.component;

import data.transpool.SingleMapEngine;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Stop;
import data.transpool.user.account.TransPoolRider;
import exception.data.StopNotFoundException;
import exception.data.TransPoolDataException;

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

    public BasicTripRequestData(BasicMap map, String riderName, String sourceStopName, String destinationStopName) throws TransPoolDataException {
        this.requestID = IDGenerator++;
        this.transpoolRider = new TransPoolRider(riderName);
        this.sourceStop = initializeStop(map, sourceStopName);
        this.destinationStop = initializeStop(map, destinationStopName);
    }

    public BasicTripRequestData(TripRequest other) {
        this.requestID = other.getRequestID();
        this.transpoolRider = new TransPoolRider(other.getTransPoolRider());
        this.sourceStop = new Stop(other.getSourceStop());
        this.destinationStop = new Stop(other.getDestinationStop());
    }

    /**
     *
     * @param map - The map to get the stop from
     * @param stopName - The stop name to get from the map
     * @return - A <underline>copy</underline> of the stop from the map.
     * @throws  - StopNotFoundException if there is not such stop with name stopName in the map.
     */
    private Stop initializeStop(BasicMap map, String stopName) throws StopNotFoundException {
        Stop stop = map.getStop(stopName);
        if (stop == null) {
            throw new StopNotFoundException(stopName);
        } else {
            return new Stop(stop);
        }
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
    public Stop getSourceStop() {
        return this.sourceStop;
    }


    @Override
    public Stop getDestinationStop() {
        return this.destinationStop;
    }

    @Override
    public boolean isBelongToMap(SingleMapEngine map) {
        return map.getTripRequest(this.requestID) != null;
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
