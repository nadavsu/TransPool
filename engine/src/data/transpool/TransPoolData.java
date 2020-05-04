package data.transpool;

import data.jaxb.TransPool;
import data.transpool.map.Map;
import data.transpool.structure.TransPoolTripRequests;
import data.transpool.structure.TransPoolTrips;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.TransPoolTripRequest;
import exception.file.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

/**
 * The class containing the TransPool data structures.
 */
public class TransPoolData {

    public static Map map;
    public static TransPoolTrips allTransPoolTrips;
    public static TransPoolTripRequests allTransPoolTripRequests;
    public static List<MatchedTransPoolTripRequest> allMatchedTrips;

    public static boolean isLoaded = false;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        isLoaded = true;
        map = new Map(JAXBData.getMapDescriptor());
        allTransPoolTrips = new TransPoolTrips(JAXBData.getPlannedTrips());
        allTransPoolTripRequests = new TransPoolTripRequests();
        allMatchedTrips = new ArrayList<>();
    }

    public static Map getMap() {
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

    public static void addTransPoolTripRequest(TransPoolTripRequest transPoolTripRequest) {
        allTransPoolTripRequests.addTransPoolTripRequest(transPoolTripRequest);
    }

    public static void addMatch(MatchedTransPoolTripRequest matchedTransPoolTripRequest) {
        allMatchedTrips.add(matchedTransPoolTripRequest);
    }
}
