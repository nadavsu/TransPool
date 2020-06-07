package api;

import data.transpool.TransPoolData;
import data.transpool.trip.offer.PossibleMatch;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.offer.TripOfferData;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import data.transpool.trip.request.TripRequestData;
import exception.NoMatchesFoundException;
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

    ObservableList<TripRequest> getAllTripRequests();

    ObservableList<TripOffer> getAllTripOffers();

    void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, String recurrences,
                            int riderCapacity, int PPK, ObservableList<String> addedStops) throws TransPoolDataException;

    void findPossibleMatches(TripRequest request, int maximumMatches) throws NoMatchesFoundException;

    void clearPossibleMatches();

    ObservableList<PossibleMatch> getPossibleMatches();

    void addNewMatch(PossibleMatch PossibleMatches);

    ObservableList<MatchedTripRequest> getAllMatchedTripRequests();

    ObservableList<Integer> getAllMatchedTripRequestIDs();

    TransPoolData getData();

    BooleanProperty fileLoadedProperty();

    BooleanProperty foundMatchesProperty();

    void initiateFeedbackEngine(int riderID);
}
