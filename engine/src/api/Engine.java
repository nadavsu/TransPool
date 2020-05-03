package api;

import data.transpool.TransPoolData;
import data.transpool.structures.TransPoolTripRequests;
import data.transpool.structures.TransPoolTrips;

public class Engine {

    protected static TransPoolData data;

    public Engine() { }

    public TransPoolData getData() {
        return data;
    }

    public TransPoolTrips getAllTransPoolTrips() {
        return TransPoolData.getAllTransPoolTrips();
    }

    public TransPoolTripRequests getAllTransPoolTripRequests() {
        return TransPoolData.getAllTransPoolTripRequests();
    }

}
