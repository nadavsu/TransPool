package api;

import data.transpool.TransPoolData;
import data.transpool.trip.offer.data.PossibleMatch;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import exception.NoMatchesFoundException;
import exception.data.InvalidDayStartException;
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
                                       int day, LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws TransPoolDataException;

    ObservableList<TripRequest> getAllTripRequests();

    ObservableList<TripOffer> getAllTripOffers();

    void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, String recurrences,
                            int riderCapacity, int PPK, ObservableList<String> addedStops) throws TransPoolDataException;

    void findPossibleMatches(TripRequest request, int maximumMatches) throws NoMatchesFoundException, TransPoolDataException;

    void clearPossibleMatches();

    ObservableList<PossibleRoute> getPossibleRoutes();

    void addNewMatch(PossibleMatch PossibleMatches);

    ObservableList<MatchedTripRequest> getAllMatchedTripRequests();

    ObservableList<Integer> getAllMatchedTripRequestIDs();

    TransPoolData getData();

    BooleanProperty fileLoadedProperty();

    BooleanProperty foundMatchesProperty();

    void initiateFeedbackEngine(int riderID);
}
