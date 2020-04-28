package data.transpool;

import data.jaxb.TransPool;
import data.transpool.map.Map;
import data.transpool.map.Stop;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TripRequest;
import exceptions.data.PathDoesNotExistException;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransPoolData {

    private Map transpoolMap;
    private List<TripRequest> tripRequests;
    private List<TransPoolTrip> transpoolTrips;

    public TransPoolData() {
        transpoolMap = new Map();
        tripRequests = new ArrayList<>();
        transpoolTrips = new ArrayList<>();
    }

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        this.transpoolTrips = new ArrayList<>();
        this.tripRequests = new ArrayList<>();
        this.transpoolMap = new Map(JAXBData.getMapDescriptor());

        List<data.jaxb.TransPoolTrip> JAXBTransPoolTripsList = JAXBData.getPlannedTrips().getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTransPoolTrip : JAXBTransPoolTripsList) {
            transpoolTrips.add(new TransPoolTrip(JAXBTransPoolTrip));
        }
    }

    public Map getTranspoolMap() {
        return transpoolMap;
    }

    public List<TripRequest> getTripRequests() {
        return tripRequests;
    }

    public List<TransPoolTrip> getTranspoolTrips() {
        return transpoolTrips;
    }

    public void addRequest(TripRequest newRequest) {
        tripRequests.add(newRequest);
    }

    public Stop getStop(String stopName) throws StopNotFoundException {
        return transpoolMap.getStop(stopName);
    }
}
