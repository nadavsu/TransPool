package api;

import api.components.*;
import api.task.LoadFileTask;
import data.transpool.TransPoolData;
import data.transpool.trip.offer.PossibleMatch;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.offer.TripOfferData;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.TripRequest;
import data.transpool.trip.request.TripRequestData;
import exception.NoMatchesFoundException;
import exception.data.StopNotFoundException;
import exception.data.TransPoolDataException;
import exception.data.TransPoolFileNotFoundException;
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

    private TransPoolController transpoolController;

    public TransPoolEngine(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
        this.matchingEngine = new MatchingEngine();

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
        TripRequest request = new TripRequestData(riderName, source, destination, time, isArrivalTime, isContinuous);
        data.addTripRequest(request);

    }

    @Override
    public void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, String recurrences,
                                   int riderCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        //Todo: create a new trip request via a task or a thread.
        data.addTripOffer(new TripOfferData(driverName, departureTime, dayStart, recurrences, riderCapacity, PPK, route));
    }

    @Override
    public void addNewMatch(PossibleMatch chosenPossibleMatch) {
        matchingEngine.addNewMatch(data, chosenPossibleMatch);
    }

    @Override
    public void findPossibleMatches(TripRequest request, int maximumMatches) throws NoMatchesFoundException {
        //Todo: Currently running from the JAT, get it off the JAT.
        matchingEngine.findPossibleMatches(data, request, maximumMatches);
    }

    @Override
    public ObservableList<BasicTripRequest> getAllTripRequests() {
        return data.getAllTripRequests();
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return data.getAllTripOffers();
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
    public TransPoolData getData() {
        return data;
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
