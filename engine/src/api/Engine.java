package api;

import data.transpool.TransPoolData;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import exception.data.PathDoesNotExistException;
import exception.data.StopNotFoundException;
import exception.data.TransPoolDataException;
import exception.data.TransPoolFileNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

public interface Engine {

    void loadFile(File file) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException, ExecutionException, InterruptedException;

    void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                       LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws StopNotFoundException;

    ObservableList<String> getAllTransPoolTripRequestsAsStrings();

    ObservableList<TransPoolTripRequest> getAllTransPoolTripRequests();

    ObservableList<TransPoolTrip> getAllTransPoolTrips();

    void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, String recurrences,
                            int riderCapacity, int PPK, ObservableList<String> addedStops) throws TransPoolDataException;

    void findPossibleMatches(TransPoolTripRequest request, int maximumMatches) throws NoMatchesFoundException;

    void clearPossibleMatches();

    ObservableList<PossibleMatch> getPossibleMatches();

    void addNewMatch(PossibleMatch PossibleMatches);

    TransPoolData getData();

    ObservableList<String> getAllTransPoolTripsAsStrings();

    BooleanProperty fileLoadedProperty();

    BooleanProperty foundMatchesProperty();
}
