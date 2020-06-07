package data.transpool;

import api.components.TripOfferEngine;
import api.components.TripRequestEngine;
import data.jaxb.PlannedTrips;
import data.jaxb.TransPool;
import data.transpool.map.Map;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.offer.TripOfferData;
import data.transpool.trip.request.TripRequest;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TransPoolData implements TripRequestEngine, TripOfferEngine {

    private Map map;
    private ObservableList<TripOffer> allTripOffers;
    private ObservableList<TripRequest> allTripRequests;
    private ObservableList<MatchedTripRequest> allMatchedTripRequests;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        allTripRequests = FXCollections.observableArrayList();
        allTripOffers = FXCollections.observableArrayList();
        allMatchedTripRequests = FXCollections.observableArrayList();
        map = new Map(JAXBData.getMapDescriptor());
        initAllTripOffers(JAXBData.getPlannedTrips());
    }

    private void initAllTripOffers(PlannedTrips JAXBTransPoolTrips) throws TransPoolDataException {
        List<data.jaxb.TransPoolTrip> JAXBTrips = JAXBTransPoolTrips.getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTrip : JAXBTrips) {
            allTripOffers.add(new TripOfferData(JAXBTrip));
        }
    }

    /**
     * Finds a trip request by given trip request ID by iterating over all trip requests.
     *
     * @param ID - The ID of the desired request.
     * @return -
     * @throws NullPointerException - If TP trip request was not found.
     *
     */
    @Override
    public TripRequest getTripRequest(int ID) {
        return allTripRequests
                .stream()
                .filter(t -> t.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteTripRequest(TripRequest requestToDelete) {
        allTripRequests.remove(requestToDelete);
    }

    @Override
    public void addTripRequest(TripRequest tripRequest) {
        allTripRequests.add(tripRequest);
    }

    @Override
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        allMatchedTripRequests.add(matchedTripRequest);
        TripOffer trip = getTripOffer(matchedTripRequest.getTripOfferID());
        trip.updateAfterMatch(matchedTripRequest);
        deleteTripRequest(getTripRequest(matchedTripRequest.getRequestID()));
    }

    @Override
    public ObservableList<TripRequest> getAllTripRequests() throws NullPointerException {
        if (allTripRequests == null) {
            throw new NullPointerException();
        }
        return allTripRequests;
    }

    @Override
    public ObservableList<MatchedTripRequest> getAllMatchedTripRequests() {
        return allMatchedTripRequests;
    }

    public MatchedTripRequest getMatchedTripRequest(int riderID) {
        return allMatchedTripRequests
                .stream()
                .filter(match -> match.getRequestID() == riderID)
                .findAny()
                .orElse(null);
    }

    /**
     * Finds a TransPoolTrip by ID.
     *
     * @param ID - The ID to search for
     * @return the TransPoolTrip with the matched ID if found, null otherwise.
     */
    @Override
    public TripOffer getTripOffer(int ID) {
        return allTripOffers
                .stream()
                .filter(t -> t.getOfferID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addTripOffer(TripOffer tripOffer) {
        allTripOffers.add(tripOffer);
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() throws NullPointerException {
        if (allTripOffers == null) {
            throw new NullPointerException();
        }
        return allTripOffers;
    }

    public Map getMap() {
        return map;
    }
}
