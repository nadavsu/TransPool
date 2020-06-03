package api;

import api.components.*;
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
        transpoolController.bindUIToData(data);

        isLoaded.set(true);
    }

    @Override
    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              LocalTime time, boolean isArrivalTime, boolean isContinuous) throws
            StopNotFoundException {
        if (!data.getMap().containsStop(source)) {
            throw new StopNotFoundException(source);
        }
        if (!data.getMap().containsStop(destination)) {
            throw new StopNotFoundException(destination);
        }
        TransPoolTripRequest request = new TransPoolTripRequest(riderName, source, destination, time, isArrivalTime, isContinuous);
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
    public void findPossibleMatches(TransPoolTripRequest request, int maximumMatches) throws NoMatchesFoundException {
        matchingEngine.findPossibleMatches(data, request, maximumMatches);
    }

    @Override
    public ObservableList<PossibleMatch> getPossibleMatches() {
        return matchingEngine.getPossibleMatches();
    }

    @Override
    public void clearPossibleMatches() {
        matchingEngine.clearPossibleMatches();
    }

    @Override
    public void addNewMatch(PossibleMatch chosenPossibleMatch) {
        matchingEngine.addNewMatch(data, chosenPossibleMatch);
    }

    @Override
    public TransPoolData getData() {
        return data;
    }

    @Override
    public ObservableList<String> getAllTransPoolTripsAsStrings() {
        return transPoolTripEngine.getAllTransPoolTripsAsStrings(data);
    }

    @Override
    public BooleanProperty fileLoadedProperty() {
        return isLoaded;
    }

    @Override
    public BooleanProperty foundMatchesProperty() {
        return matchingEngine.foundMatchesProperty();
    }
}
