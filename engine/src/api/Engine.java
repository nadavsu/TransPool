package api;

import data.transpool.TransPoolMapBase;
import data.transpool.time.component.Recurrence;
import data.transpool.time.component.TimeInterval;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.user.component.feedback.Feedbackable;
import data.transpool.user.component.feedback.Feedbacker;
import exception.NoResultsFoundException;
import exception.data.TransPoolDataException;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface Engine {

    void loadFile(File file) throws ExecutionException, InterruptedException, JAXBException, TransPoolDataException;

    void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                       int day, LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws TransPoolDataException;

    List<TripRequest> getAllTripRequests();

    List<TripOffer> getAllTripOffers();

    void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences,
                            int riderCapacity, int PPK, ObservableList<String> addedStops) throws TransPoolDataException;

    void createNewFeedback(Feedbacker feedbacker, Feedbackable feedbackee, int rating, String comment);

    void findPossibleMatches(TripRequest request, int maximumMatches) throws NoResultsFoundException, TransPoolDataException;

    void clearPossibleMatches();

    void incrementTime(TimeInterval duration);

    void decrementTime(TimeInterval duration);

    List<PossibleRoute> getPossibleRoutes();

    void addNewMatch(int possibleMatchIndex) throws TransPoolDataException;

    List<MatchedTripRequest> getAllMatchedTripRequests();

    List<Integer> getAllMatchedTripRequestIDs();

    TransPoolMapBase getData();

    boolean isFoundMatches();

    void setData(TransPoolMapBase data);
}
