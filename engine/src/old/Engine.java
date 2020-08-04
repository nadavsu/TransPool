package old;

import api.transpool.TransPoolMap;
import api.transpool.time.component.Recurrence;
import api.transpool.time.component.TimeInterval;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.matching.component.PossibleRoute;
import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.component.feedback.Feedbackable;
import api.transpool.user.component.feedback.Feedbacker;
import exception.parser.NoResultsFoundException;
import exception.parser.TransPoolDataException;
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

    void createNewTripOffer(TransPoolDriver driver, LocalTime departureTime, int dayStart, Recurrence recurrences,
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

    TransPoolMap getData();

    boolean isFoundMatches();

    void setData(TransPoolMap data);
}
