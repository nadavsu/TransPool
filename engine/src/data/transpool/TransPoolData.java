package data.transpool;

import data.jaxb.PlannedTrips;
import data.jaxb.TransPool;
import data.transpool.map.Map;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.file.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

/**
 * The class containing the TransPool data structures.
 */
public class TransPoolData {

    private Map map;
    private List<TransPoolTrip> allTransPoolTrips;
    private List<TransPoolTripRequest> allTransPoolTripRequests;
    private List<MatchedTransPoolTripRequest> allMatchedTrips;

    public static boolean isLoaded = false;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        allTransPoolTripRequests = new ArrayList<>();
        allTransPoolTrips = new ArrayList<>();
        allMatchedTrips = new ArrayList<>();
        allMatchedTrips = new ArrayList<>();
        isLoaded = true;
        map = new Map(JAXBData.getMapDescriptor());
        initTransPoolTrips(JAXBData.getPlannedTrips());
    }

    //------------------------------------------------------------------------------------------
    private void initTransPoolTrips(PlannedTrips JAXBTransPoolTrips) throws TransPoolDataException {
        List<data.jaxb.TransPoolTrip> JAXBTrips = JAXBTransPoolTrips.getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTrip : JAXBTrips) {
            allTransPoolTrips.add(new TransPoolTrip(JAXBTrip));
        }
    }

    /**
     * Finds a TransPoolTrip by ID.
     *
     * @param ID - The ID to search for
     * @return the TransPoolTrip with the matched ID if found, null otherwise.
     */
    public TransPoolTrip getTransPoolTripByID(int ID) {
        return allTransPoolTrips
                .stream()
                .filter(t -> t.getID() == ID)
                .findFirst()
                .orElse(null);
    }

    //------------------------------------------------------------------------------------------

    /**
     * Finds a trip request by given trip request ID by iterating over all trip requests.
     *
     * @param ID - The ID of the desired request.
     * @return -
     * @throws NullPointerException - If TP trip request was not found.
     *                              TODO: Create a TransPoolTripRequestNotFoundException and throw it here?
     */
    public TransPoolTripRequest getTripRequestByID(int ID) throws NullPointerException {
        return allTransPoolTripRequests
                .stream()
                .filter(t -> t.getID() == ID)
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    public void deleteTripRequest(TransPoolTripRequest requestToDelete) {
        allTransPoolTripRequests.remove(requestToDelete);
    }

    public void addTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest) {
        allTransPoolTripRequests.add(transpoolTripRequest);
    }

    //------------------------------------------------------------------------------------------
    public Map getMap() {
        return map;
    }

    public List<TransPoolTripRequest> getAllTransPoolTripRequests() throws NullPointerException {
        if (allTransPoolTripRequests == null) {
            throw new NullPointerException();
        }
        return allTransPoolTripRequests;
    }

    public List<TransPoolTrip> getAllTransPoolTrips() throws NullPointerException {
        if (allTransPoolTrips == null) {
            throw new NullPointerException();
        }
        return allTransPoolTrips;
    }

    //------------------------------------------------------------------------------------------
    public List<MatchedTransPoolTripRequest> getAllMatchedTrips() {
        return allMatchedTrips;
    }

    public void addMatch(MatchedTransPoolTripRequest matchedTransPoolTripRequest) {
        //Adding the match
        allMatchedTrips.add(matchedTransPoolTripRequest);

        //Updating the data and deleting the request.
        TransPoolTrip trip = getTransPoolTripByID(matchedTransPoolTripRequest.getTranspoolTripID());
        trip.updateAfterMatch(matchedTransPoolTripRequest);
        deleteTripRequest(getTripRequestByID(matchedTransPoolTripRequest.getRequestID()));
    }
}
