package api;

import api.components.FileEngine;
import api.components.MatchingEngine;
import api.components.TransPoolTripEngine;
import api.components.TransPoolTripRequestEngine;
import api.components.main.TransPoolController;
import data.transpool.TransPoolData;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import exception.file.StopNotFoundException;
import exception.file.TransPoolDataException;
import exception.file.TransPoolFileNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalTime;
import java.util.List;

public class TransPoolEngine implements Engine {

    private TransPoolData data;
    private BooleanProperty isLoaded;

    private FileEngine fileEngine;
    private MatchingEngine matchingEngine;
    private TransPoolTripEngine transPoolTripEngine;
    private TransPoolTripRequestEngine transPoolTripRequestEngine;

    private TransPoolController controller;

    public TransPoolEngine(TransPoolController controller) {
        this.controller = controller;
        this.fileEngine = new FileEngine();
        this.matchingEngine = new MatchingEngine();
        this.transPoolTripEngine = new TransPoolTripEngine();
        this.transPoolTripRequestEngine = new TransPoolTripRequestEngine();
        this.isLoaded = new SimpleBooleanProperty(false);
    }

    @Override
    public void loadFile(File file) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException {
        data = fileEngine.loadData(file);
        isLoaded.set(true);
    }

    @Override
    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              LocalTime time, boolean isArrivalTime, boolean isContinuous) throws
            StopNotFoundException {
        TransPoolTripRequest request = transPoolTripRequestEngine.createNewTransPoolTripRequest(riderName, source, destination, time, isArrivalTime, isContinuous);
        data.addTransPoolTripRequest(request);
    }

    @Override
    public ObservableList<String> getAllTransPoolTripRequestsAsStrings() {
        return transPoolTripRequestEngine.getAllTransPoolTripRequestsAsStrings(data);
    }

    @Override
    public ObservableList<TransPoolTripRequest> getAllTransPoolTripRequests() {
        return transPoolTripRequestEngine.getAllTransPoolTripRequests(data);
    }

    @Override
    public ObservableList<TransPoolTrip> getAllTransPoolTrips() {
        return transPoolTripEngine.getAllTransPoolTrips(data);
    }

    @Override
    public void findPossibleMatches(int tripRequestID, int maximumMatches) throws NoMatchesFoundException {
        matchingEngine.findPossibleMatches(data, tripRequestID, maximumMatches);
    }

    @Override
    public List<PossibleMatch> getPossibleMatches() {
        return matchingEngine.getPossibleMatches();
    }

    @Override
    public void addNewMatch(int chosenPossibleMatchIndex) {
        matchingEngine.addNewMatch(data, chosenPossibleMatchIndex);
    }

    @Override
    public TransPoolData getData() {
        return data;
    }

    @Override
    public BooleanProperty fileLoadedProperty() {
        return isLoaded;
    }

    @Override
    public ObservableList<String> getAllTransPoolTripsAsStrings() {
        return transPoolTripEngine.getAllTransPoolTripsAsStrings(data);
    }
}
