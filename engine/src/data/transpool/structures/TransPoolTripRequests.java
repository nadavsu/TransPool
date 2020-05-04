package data.transpool.structures;

import data.transpool.trips.TransPoolTripRequest;

import java.util.ArrayList;
import java.util.List;

public class TransPoolTripRequests {
    private List<TransPoolTripRequest> transpoolTripRequests;

    public TransPoolTripRequests() {
        transpoolTripRequests = new ArrayList<>();
    }

    public void addTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest) {
        transpoolTripRequests.add(transpoolTripRequest);
    }

    public List<TransPoolTripRequest> getTranspoolTripRequests() {
        return transpoolTripRequests;
    }

    /**
     * Finds a trip request by given trip request ID by iterating over all trip requests.
     * @param ID - The ID of the desired request.
     * @return -
     * @throws NullPointerException - If TP trip request was not found.
     * TODO: Create a TransPoolTripRequestNotFoundException and throw it here?
     */
    public TransPoolTripRequest getTripRequestByID(int ID) throws NullPointerException {
        return transpoolTripRequests
                .stream()
                .filter(t -> t.getID() == ID)
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    public void deleteTripRequest(TransPoolTripRequest requestToDelete) {
        transpoolTripRequests.remove(requestToDelete);
    }

    @Override
    public String toString() {
        StringBuilder allRequests = new StringBuilder();
        for (TransPoolTripRequest request : transpoolTripRequests) {
            allRequests.append(request.toString());
            allRequests.append("\n");
        }
        return allRequests.toString();
    }
}
