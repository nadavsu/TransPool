package data.transpool;

import data.generated.TransPool;
import data.transpool.map.Map;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TripRequest;

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

    public TransPoolData(TransPool JAXBData) {
        this.transpoolMap = new Map(JAXBData.getMapDescriptor());
        transpoolTrips = JAXBData
                .getPlannedTrips()
                .getTransPoolTrip()
                .stream()
                .map(TransPoolTrip::new)
                .collect(Collectors.toList());
        tripRequests = new ArrayList<>();
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
}
