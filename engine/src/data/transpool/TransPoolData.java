package data.transpool;

import data.jaxb.TransPool;
import data.transpool.map.TransPoolMap;
import data.transpool.map.Stop;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TripRequest;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolData {

    private TransPoolMap map;
    private List<TripRequest> tripRequests;
    private List<TransPoolTrip> transpoolTrips;

    public TransPoolData() {
        map = new TransPoolMap();
        tripRequests = new ArrayList<>();
        transpoolTrips = new ArrayList<>();
    }

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        this.transpoolTrips = new ArrayList<>();
        this.tripRequests = new ArrayList<>();
        this.map = new TransPoolMap(JAXBData.getMapDescriptor());

        List<data.jaxb.TransPoolTrip> JAXBTransPoolTripsList = JAXBData.getPlannedTrips().getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTransPoolTrip : JAXBTransPoolTripsList) {
            addTransPoolTrip(new TransPoolTrip(JAXBTransPoolTrip, map));
        }
    }

    public TransPoolMap getMap() {
        return map;
    }

    public List<TripRequest> getTripRequests() {
        return tripRequests;
    }

    public List<TransPoolTrip> getTransPoolTrips() {
        return transpoolTrips;
    }

    public void addRequest(TripRequest newRequest) {
        tripRequests.add(newRequest);
    }

    public Stop getStop(String stopName) throws StopNotFoundException {
        try {
            return map.getStop(stopName);
        } catch (NullPointerException e) {
            throw new StopNotFoundException(stopName);
        }
    }

    private void addTransPoolTrip(TransPoolTrip transpoolTrip) {
        transpoolTrips.add(transpoolTrip);
    }
}
