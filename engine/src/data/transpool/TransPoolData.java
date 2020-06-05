package data.transpool;

import data.jaxb.PlannedTrips;
import data.jaxb.TransPool;
import data.transpool.map.Map;
import data.transpool.trip.MatchedTransPoolTripRequest;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * The class containing the TransPool data structures.
 */
public class TransPoolData {

    private Map map;
    private ObservableList<TransPoolTrip> allTransPoolTrips;
    private ObservableList<TransPoolTripRequest> allTransPoolTripRequests;
    private ObservableList<MatchedTransPoolTripRequest> allMatchedTrips;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        allTransPoolTripRequests = FXCollections.observableArrayList();
        allTransPoolTrips = FXCollections.observableArrayList();
        allMatchedTrips = FXCollections.observableArrayList();
        allMatchedTrips = FXCollections.observableArrayList();
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
                .filter(t -> t.getOfferID() == ID)
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
     *
     */
    public TransPoolTripRequest getTripRequestByID(int ID) {
        return allTransPoolTripRequests
                .stream()
                .filter(t -> t.getRequestID() == ID)
                .findFirst()
                .orElse(null);
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

    public ObservableList<TransPoolTripRequest> getAllTransPoolTripRequests() throws NullPointerException {
        if (allTransPoolTripRequests == null) {
            throw new NullPointerException();
        }
        return allTransPoolTripRequests;
    }

    public ObservableList<TransPoolTrip> getAllTransPoolTrips() throws NullPointerException {
        if (allTransPoolTrips == null) {
            throw new NullPointerException();
        }
        return allTransPoolTrips;
    }

    //------------------------------------------------------------------------------------------
    public ObservableList<MatchedTransPoolTripRequest> getAllMatchedTrips() {
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

    public void addTransPoolTrip(TransPoolTrip transPoolTrip) {
        allTransPoolTrips.add(transPoolTrip);
    }

/*    public static Callback<TransPoolTrip, Observable[]> extractor() {
        return (TransPoolTrip t) -> new Observable[]{t.lastNameProperty(), t.firstNameProperty(), t.birthdayProperty(), t.ageBinding()};
    }*/
}
