package api;

import com.fxgraph.graph.Graph;
import data.transpool.TransPoolData;
import data.transpool.trip.Recurrence;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import data.transpool.user.Feedbackable;
import data.transpool.user.Feedbacker;
import data.transpool.user.TransPoolDriver;
import exception.NoResultsFoundException;
import exception.data.TransPoolDataException;
import exception.data.TransPoolFileNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

public interface Engine {

    void loadFile(File file) throws ExecutionException, InterruptedException;

    void createMap(Graph mapGraph);

    void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                       int day, LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws TransPoolDataException;

    ObservableList<TripRequest> getAllTripRequests();

    ObservableList<TripOffer> getAllTripOffers();

    void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences,
                            int riderCapacity, int PPK, ObservableList<String> addedStops) throws TransPoolDataException;

    void createNewFeedback(Feedbacker feedbacker, Feedbackable feedbackee, int rating, String comment);

    void findPossibleMatches(TripRequest request, int maximumMatches) throws NoResultsFoundException, TransPoolDataException;

    void clearPossibleMatches();

    void incrementTime(Duration duration);

    void decrementTime(Duration duration);

    ObservableList<PossibleRoute> getPossibleRoutes();

    void addNewMatch(int possibleMatchIndex);

    ObservableList<MatchedTripRequest> getAllMatchedTripRequests();

    ObservableList<Integer> getAllMatchedTripRequestIDs();

    TransPoolData getData();

    BooleanProperty fileLoadedProperty();

    BooleanProperty foundMatchesProperty();
}
