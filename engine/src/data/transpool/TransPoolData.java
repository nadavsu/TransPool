package data.transpool;

import data.jaxb.TransPool;
import data.transpool.map.Map;
import data.transpool.map.Stop;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TripRequest;
import exceptions.data.InvalidRouteException;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolData {

    private Map map;
    private List<TripRequest> tripRequests;
    private List<TransPoolTrip> transpoolTrips;

    public TransPoolData() {
        map = new Map();
        tripRequests = new ArrayList<>();
        transpoolTrips = new ArrayList<>();
    }

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        this.transpoolTrips = new ArrayList<>();
        this.tripRequests = new ArrayList<>();
        this.map = new Map(JAXBData.getMapDescriptor());

        List<data.jaxb.TransPoolTrip> JAXBTransPoolTripsList = JAXBData.getPlannedTrips().getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTransPoolTrip : JAXBTransPoolTripsList) {
            addTransPoolTrip(new TransPoolTrip(JAXBTransPoolTrip));
        }
    }

    public Map getMap() {
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
        return map.getStop(stopName);
    }

    private void addTransPoolTrip(TransPoolTrip transpoolTrip) throws InvalidRouteException {
        List<String> routeStopNamesList = transpoolTrip.getRouteAsList();
        for (int i = 0; i < routeStopNamesList.size() - 1; i++) {
            if (map.getPath(routeStopNamesList.get(i), routeStopNamesList.get(i + 1)) == null) {
                throw new InvalidRouteException();
            }
        }
        transpoolTrips.add(transpoolTrip);
    }
}
