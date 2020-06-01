package api;

import api.components.*;
import api.components.cards.request.BasicTripRequestData;
import api.components.cards.request.TripRequestData;
import api.task.LoadFileTask;
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
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransPoolEngine implements Engine {

    private TransPoolData data;
    private BooleanProperty isLoaded;

    private Task currentRunningTask;

    private MatchingEngine matchingEngine;
    private TransPoolTripEngine transPoolTripEngine;
    private TransPoolTripRequestEngine transPoolTripRequestEngine;

    private TransPoolController transpoolController;

    public TransPoolEngine(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
        this.matchingEngine = new MatchingEngine();
        this.transPoolTripEngine = new TransPoolTripEngine();
        this.transPoolTripRequestEngine = new TransPoolTripRequestEngine();
        this.isLoaded = new SimpleBooleanProperty(false);
    }

    @Override
    public void loadFile(File file) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException, ExecutionException, InterruptedException {
        currentRunningTask = new LoadFileTask(file);
        transpoolController.bindTaskToUI(currentRunningTask);
        //TODO: handle exceptions.
        new Thread(currentRunningTask).start();

        data = (TransPoolData) currentRunningTask.get();
        isLoaded.set(true);
    }

    @Override
    public void createNewTransPoolTripRequest(UICardAdapter<TripRequestData> adapter, String riderName, String source, String destination,
                                              LocalTime time, boolean isArrivalTime, boolean isContinuous) throws
            StopNotFoundException {
        //Todo: you stopped here. you need to create a task inside create a new UI adapter which will createCard()
        TransPoolTripRequest request = new TransPoolTripRequest(data.getMap(), riderName, source, destination, time, isArrivalTime, isContinuous);
        data.addTransPoolTripRequest(request);
        TripRequestData tripRequestData = new BasicTripRequestData(request.getID(), riderName, source, destination, time, isArrivalTime, isContinuous);
        adapter.addCard(tripRequestData);
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
