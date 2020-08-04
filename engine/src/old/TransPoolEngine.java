package old;

import api.generated.TransPool;
import api.transpool.TransPoolMap;
import api.transpool.time.component.Recurrence;
import api.transpool.time.component.TimeInterval;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.matching.component.PossibleRoute;
import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.user.account.TransPoolDriver;
import api.transpool.user.component.feedback.Feedback;
import api.transpool.user.component.feedback.Feedbackable;
import api.transpool.user.component.feedback.Feedbacker;
import exception.parser.NoResultsFoundException;
import exception.time.InvalidDayStartException;
import exception.parser.StopNotFoundException;
import exception.parser.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalTime;
import java.util.List;


/**
 * The main engine of the application.
 * Holds the data, and other engines.
 */

//Everything here will eventually be a serlvlet.
public class TransPoolEngine implements Engine {
    private TransPoolMap data;
    private MatchingEngineOLD matchingEngine;

    public TransPoolEngine() {
        this.matchingEngine = new MatchingEngineOLD();
    }

    @Override
    //DONE IN SERVLET.
    public void loadFile(File file) throws JAXBException, TransPoolDataException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(file);

        data = new TransPoolMap("generic_name", "generic_username", JAXBData);
    }

    //DONE IN SERVLET
    @Override
    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              int day, LocalTime time, boolean isArrivalTime, boolean isContinuous) throws
            TransPoolDataException {
        if (!data.containsStop(source)) {
            throw new StopNotFoundException(source);
        }
        if (!data.containsStop(destination)) {
            throw new StopNotFoundException(destination);
        }
        //TripRequest request = new TripRequestData(, riderName, data.getStop(source), data.getStop(destination), day, time, isArrivalTime, isContinuous);
        //data.addTripRequest(request);

    }

    //DONE IN SERVLET
    @Override
    public void createNewTripOffer(TransPoolDriver driver, LocalTime departureTime, int dayStart, Recurrence recurrences,
                                   int riderCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        if (dayStart < 1) {
            throw new InvalidDayStartException();
        }
        data.addTripOffer(new TripOffer(data.getMap(), driver, departureTime, dayStart, recurrences, riderCapacity, PPK, route));
    }


    //DONE IN SERVLET
    @Override
    public void findPossibleMatches(TripRequest request, int maximumMatches) throws NoResultsFoundException {
        matchingEngine.findPossibleMatches(data, request, maximumMatches);
    }

    //DONE IN SERVLET
    @Override
    public void createNewFeedback(Feedbacker feedbacker, Feedbackable feedbackee, int rating, String comment) {
        feedbacker.leaveFeedback(feedbackee,
                new Feedback(feedbacker, rating, comment)
        );
    }

    @Override
    public void setData(TransPoolMap data) {
        this.data = data;
    }

    //DONE IN SERVLET
    @Override
    public void addNewMatch(int possibleMatchIndex) throws TransPoolDataException {
        matchingEngine.addNewMatch(data, possibleMatchIndex);
    }

    @Override
    public void incrementTime(TimeInterval duration) {
        data.incrementTime(duration);
    }

    @Override
    public void decrementTime(TimeInterval duration) {
        data.decrementTime(duration);
    }

    @Override
    public List<TripRequest> getAllTripRequests() {
        return data.getAllTripRequests();
    }

    @Override
    public List<TripOffer> getAllTripOffers() {
        return data.getAllTripOffers();
    }

    @Override
    public List<PossibleRoute> getPossibleRoutes() {
        return matchingEngine.getPossibleRoutes();
    }

    @Override
    public void clearPossibleMatches() {
        matchingEngine.clearPossibleMatches();
    }

    @Override
    public List<MatchedTripRequest> getAllMatchedTripRequests() {
        return data.getAllMatchedTripRequests();
    }

    @Override
    public ObservableList<Integer> getAllMatchedTripRequestIDs() {
        ObservableList<Integer> matchedTripsIDs = FXCollections.observableArrayList();
        data
                .getAllMatchedTripRequests()
                .forEach(match -> matchedTripsIDs.add(match.getRequestID()));
        return matchedTripsIDs;
    }

    @Override
    public TransPoolMap getData() {
        return data;
    }

    @Override
    public boolean isFoundMatches() {
        return matchingEngine.isFoundMatches();
    }
}
