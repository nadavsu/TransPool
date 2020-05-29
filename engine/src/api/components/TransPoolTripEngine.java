package api.components;

import api.controller.TransPoolTripOfferController;
import data.transpool.TransPoolData;
import data.transpool.trip.TransPoolTrip;

import java.util.List;

public class TransPoolTripEngine {
    public void createNewTransPoolTripOffer() {

    }

    public List<TransPoolTrip> getAllTransPoolTrips(TransPoolData data) {
        return data.getAllTransPoolTrips();
    }

}
