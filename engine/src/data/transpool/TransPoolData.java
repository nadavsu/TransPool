package data.transpool;

import data.jaxb.PlannedTrips;
import data.jaxb.TransPool;
import exceptions.data.PathDoesNotExistException;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolData {

    private TransPoolMap map;
    private List<TransPoolTrip> transpoolTrips = new ArrayList<>();
    private List<TransPoolTripRequest> transpoolTripRequests = new ArrayList<>();

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException, InvalidTimeException {
        map = new TransPoolMap(JAXBData.getMapDescriptor());
        initTransPoolTrips(JAXBData.getPlannedTrips());
    }

    private void initTransPoolTrips(PlannedTrips JAXBTransPoolTrips) throws TransPoolDataException, InvalidTimeException {
        List<data.jaxb.TransPoolTrip> JAXBTrips = JAXBTransPoolTrips.getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTrip : JAXBTrips) {
            addTransPoolTrip(new TransPoolTrip(JAXBTrip));
        }
    }

    private void addTransPoolTrip(TransPoolTrip transpoolTrip) throws PathDoesNotExistException {
        List<String> tripRoute = transpoolTrip.getRoute();
        for (int i = 0; i <  tripRoute.size() - 1; i++) {
            String source = tripRoute.get(i);
            String destination = tripRoute.get(i + 1);
            if (map.getPathBySourceAndDestination(source, destination) == null) {
                throw new PathDoesNotExistException(source, destination);
            }
        }
        transpoolTrips.add(transpoolTrip);
    }

    public void addTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest) {
        transpoolTripRequests.add(transpoolTripRequest);
    }

    public TransPoolMap getMap() {
        return map;
    }

    public List<TransPoolTrip> getTranspoolTrips() {
        return transpoolTrips;
    }

    public List<TransPoolTripRequest> getTranspoolTripRequests() {
        return transpoolTripRequests;
    }
}
