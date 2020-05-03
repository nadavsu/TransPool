package data.transpool;

import data.jaxb.TransPool;
import data.transpool.structures.TransPoolMap;
import data.transpool.structures.TransPoolTripRequests;
import data.transpool.structures.TransPoolTrips;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolData {

    private static TransPoolMap map;
    private static TransPoolTrips allTransPoolTrips;
    private static TransPoolTripRequests allTransPoolTripRequests;
    private static List<MatchedTransPoolTripRequest> allMatchedTrips;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException, InvalidTimeException {
        map = new TransPoolMap(JAXBData.getMapDescriptor());
        allTransPoolTrips = new TransPoolTrips(JAXBData.getPlannedTrips());
        allTransPoolTripRequests = new TransPoolTripRequests();
        allMatchedTrips = new ArrayList<>();
    }

    public static TransPoolMap getMap() {
        return map;
    }

    public static TransPoolTripRequests getAllTransPoolTripRequests() throws NullPointerException {
        if (allTransPoolTripRequests == null) {
            throw new NullPointerException();
        }
        return allTransPoolTripRequests;
    }

    public static TransPoolTrips getAllTransPoolTrips() throws NullPointerException {
        if (allTransPoolTrips == null) {
            throw new NullPointerException();
        }
        return allTransPoolTrips;
    }

    public static List<MatchedTransPoolTripRequest> getAllMatchedTrips() {
        return allMatchedTrips;
    }

    public void addMatch(MatchedTransPoolTripRequest matchedTransPoolTripRequest) {
        allMatchedTrips.add(matchedTransPoolTripRequest);
    }
}
