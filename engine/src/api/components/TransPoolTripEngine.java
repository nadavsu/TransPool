package api.components;

import api.controller.TransPoolTripOfferController;
import data.transpool.TransPoolData;
import data.transpool.trip.TransPoolTrip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class TransPoolTripEngine {
    public void createNewTransPoolTripOffer() {

    }

    public ObservableList<TransPoolTrip> getAllTransPoolTrips(TransPoolData data) {
        return data.getAllTransPoolTrips();
    }

    public ObservableList<String> getAllTransPoolTripsAsStrings(TransPoolData data) {
        return data.getAllTransPoolTrips()
                .stream()
                .map(TransPoolTrip::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
