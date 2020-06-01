package api;

import api.components.UICardAdapter;
import api.components.cards.request.TripRequestData;
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
import java.util.concurrent.ExecutionException;

public interface Engine {

    void loadFile(File file) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException, ExecutionException, InterruptedException;

    void createNewTransPoolTripRequest(UICardAdapter<TripRequestData> adapter, String riderName, String source, String destination,
                                       LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws StopNotFoundException;

    ObservableList<String> getAllTransPoolTripRequestsAsStrings();

    ObservableList<TransPoolTripRequest> getAllTransPoolTripRequests();

    ObservableList<TransPoolTrip> getAllTransPoolTrips();

    void findPossibleMatches(int tripRequestID, int maximumMatches) throws NoMatchesFoundException;

    List<PossibleMatch> getPossibleMatches();

    void addNewMatch(int indexOfPossibleMatchesList);

    TransPoolData getData();

    BooleanProperty fileLoadedProperty();

    ObservableList<String> getAllTransPoolTripsAsStrings();
}
