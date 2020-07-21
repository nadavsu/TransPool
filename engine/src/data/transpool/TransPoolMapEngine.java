package data.transpool;

import data.generated.TransPool;
import data.transpool.map.BasicMap;
import data.transpool.map.BasicMapData;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.time.TimeEngine;
import data.transpool.time.TimeEngineBase;
import data.transpool.time.component.TimeInterval;
import data.transpool.trip.offer.TripOfferEngine;
import data.transpool.trip.offer.component.SubTripOffer;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.offer.TripOfferEngineBase;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.request.TripRequestEngine;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.trip.request.TripRequestEngineBase;
import data.transpool.user.UserEngine;
import data.transpool.user.UserEngineBase;
import exception.data.TransPoolDataException;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The main class which holds the model of the system.
 * MapGraph - holds the live map model and the basic map data.
 * TripOfferEngine - holds the trip offer data.
 * TripRequestEngine - The engine for trip requests and matched requests - holds the data.
 * TimeEngine - Controls the system time.
 */

public class TransPoolMapEngine implements TransPoolMap {

    private BasicMap map;
    private TripOfferEngine tripOfferEngine;
    private TripRequestEngine tripRequestEngine;
    private TimeEngine timeEngine;

    private List<Updatable> updatables;

    public TransPoolMapEngine(TransPool JAXBData) throws TransPoolDataException {
        Stop.resetIDGenerator();
        this.map = new BasicMapData(JAXBData.getMapDescriptor());

        this.tripRequestEngine = new TripRequestEngineBase();
        this.tripOfferEngine = new TripOfferEngineBase(map, JAXBData.getPlannedTrips().getTransPoolTrip());
        this.timeEngine = new TimeEngineBase();

        this.updatables = new ArrayList<>();
        initUpdatables();
    }

    private void initUpdatables() {
        this.updatables.add(tripOfferEngine);
    }

    @Override
    public TripRequestEngine getTripRequestEngine() {
        return tripRequestEngine;
    }

    @Override
    public TripOfferEngine getTripOfferEngine() {
        return tripOfferEngine;
    }

    @Override
    public BasicMap getMap() {
        return map;
    }

    @Override
    public TimeEngine getTimeEngine() {
        return timeEngine;
    }

    @Override
    public void update() {
        updatables.forEach(Updatable::update);
    }

    public TripOffer getTripOffer(int ID) {
        return tripOfferEngine.getTripOffer(ID);
    }

    public void addTripOffer(TripOffer tripOffer) {
        tripOfferEngine.addTripOffer(tripOffer);
    }

    public ObservableList<TripOffer> getAllTripOffers() {
        return tripOfferEngine.getAllTripOffers();
    }

    public ObservableList<TripOffer> getCurrentOffers() {
        return tripOfferEngine.getCurrentOffers();
    }

    public List<SubTripOffer> getCurrentSubTripOffers() {
        return tripOfferEngine.getCurrentSubTripOffers();
    }

    public SubTripOffer getSubTripOffer(int tripOfferID, int subTripOfferID) {
        return tripOfferEngine.getSubTripOffer(tripOfferID, subTripOfferID);
    }

    public TripRequest getTripRequest(int TripRequestID) {
        return tripRequestEngine.getTripRequest(TripRequestID);
    }

    public MatchedTripRequest getMatchedTripRequest(int MatchedTripRequestID) {
        return tripRequestEngine.getMatchedTripRequest(MatchedTripRequestID);
    }

    public void deleteTripRequest(TripRequest requestToDelete) {
        tripRequestEngine.deleteTripRequest(requestToDelete);
    }

    public void addTripRequest(TripRequest tripRequest) {
        tripRequestEngine.addTripRequest(tripRequest);
    }

    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        tripRequestEngine.addMatchedRequest(matchedTripRequest);
    }

    public ObservableList<TripRequest> getAllTripRequests() {
        return tripRequestEngine.getAllTripRequests();
    }

    public ObservableList<MatchedTripRequest> getAllMatchedTripRequests() {
        return tripRequestEngine.getAllMatchedTripRequests();
    }

    public int getMapWidth() {
        return map.getMapWidth();
    }

    public int getMapLength() {
        return map.getMapLength();
    }

    public boolean containsStop(String stopName) {
        return map.containsStop(stopName);
    }

    public Map<String, Stop> getAllStops() {
        return map.getAllStops();
    }

    public List<Stop> getAllStopsAsList() {
        return map.getAllStopsAsList();
    }

    public Stop getStop(String stopName) {
        return map.getStop(stopName);
    }

    public int getNumberOfStops() {
        return map.getNumberOfStops();
    }

    public List<Path> getAllPaths() {
        return map.getAllPaths();
    }

    public boolean containsPath(String source, String destination) {
        return map.containsPath(source, destination);
    }

    public Path getPath(Stop source, Stop destination) {
        return map.getPath(source, destination);
    }

    public Path getPath(String source, String destination) {
        return map.getPath(source, destination);
    }

    public void incrementTime(TimeInterval interval) {
        timeEngine.incrementTime(interval, this);
    }

    public void decrementTime(TimeInterval interval) {
        timeEngine.decrementTime(interval, this);
    }

    public TimeDay getCurrentTime() {
        return TimeEngineBase.currentTime;
    }

    public PossibleRoutesList getAllPossibleRoutes(TripRequest tripRequest, int maximumMatches) {
        return tripOfferEngine.getAllPossibleRoutes(tripRequest, maximumMatches);
    }

}
