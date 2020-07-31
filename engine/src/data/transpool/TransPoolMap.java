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
import data.transpool.trip.offer.TripOffersEngine;
import data.transpool.trip.offer.TripOffersEngineBase;
import data.transpool.trip.offer.component.TripOfferDTO;
import data.transpool.trip.offer.component.TripOfferPart;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.request.TripRequestEngine;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestDTO;
import data.transpool.trip.request.component.TripRequest;
import data.transpool.trip.request.TripRequestEngineBase;
import data.transpool.trip.request.component.TripRequestDTO;
import exception.data.TransPoolDataException;

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

public class TransPoolMap implements SingleMapEngine {

    private String mapName;
    private String uploaderName;

    private BasicMap map;
    private TripOffersEngine tripOffersEngine;
    private TripRequestEngine tripRequestEngine;
    private TimeEngine timeEngine;

    private List<Updatable> updatables;

    public TransPoolMap(String mapName, String uploaderName, TransPool JAXBData) throws TransPoolDataException {
        //TODO: this will create a problem when uploading multiple files. or would it?
        Stop.resetIDGenerator();

        this.mapName = mapName;
        this.uploaderName = uploaderName;

        this.map = new BasicMapData(JAXBData.getMapDescriptor());
        this.tripRequestEngine = new TripRequestEngineBase();
        this.tripOffersEngine = new TripOffersEngineBase(map);
        this.timeEngine = new TimeEngineBase();

        this.updatables = new ArrayList<>();
        initUpdatables();
    }

    private void initUpdatables() {
        this.updatables.add(tripOffersEngine);
    }

    //Basic Components-------------------------------------------------------------//
    @Override
    public MapDetailsDTO getMapDetails() {
        return new MapDetailsDTO(this);
    }

    @Override
    public String getMapName() {
        return mapName;
    }

    @Override
    public String getUploaderName() {
        return uploaderName;
    }

    @Override
    public void update() {
        updatables.forEach(Updatable::update);
    }

    @Override
    public BasicMap getMap() {
        return map;
    }

    //TripOfferEngine-------------------------------------------------------------//


    @Override
    public List<TripOfferDTO> getTripOffersDetails() {
        return tripOffersEngine.getTripOffersDetails();
    }

    @Override
    public void addTripOffer(TripOffer tripOffer) {
        tripOffersEngine.addTripOffer(tripOffer);
    }

    @Override
    public List<TripOffer> getAllTripOffers() {
        return tripOffersEngine.getAllTripOffers();
    }

    @Override
    public int getNumOfTripOffers() {
        return tripOffersEngine.getNumOfTripOffers();
    }

    @Override
    public TripOffer getTripOffer(int ID) {
        return tripOffersEngine.getTripOffer(ID);
    }

    @Override
    public List<TripOffer> getCurrentOffers() {
        return tripOffersEngine.getCurrentOffers();
    }

    @Override
    public List<TripOfferPart> getCurrentTripOfferParts() {
        return tripOffersEngine.getCurrentTripOfferParts();
    }

    @Override
    public TripOfferPart getSubTripOffer(int tripOfferID, int subTripOfferID) {
        return tripOffersEngine.getSubTripOffer(tripOfferID, subTripOfferID);
    }

    @Override
    public PossibleRoutesList getAllPossibleRoutes(TripRequest tripRequest, int maximumMatches) {
        return tripOffersEngine.getAllPossibleRoutes(tripRequest, maximumMatches);
    }

    //TripRequestEngine------------------------------------------------------------//

    @Override
    public List<TripRequestDTO> getTripRequestsDetails() {
        return tripRequestEngine.getTripRequestsDetails();
    }

    @Override
    public List<MatchedTripRequestDTO> getMatchedTripsDetails() {
        return tripRequestEngine.getMatchedTripsDetails();
    }

    @Override
    public void addTripRequest(TripRequest tripRequest) {
        tripRequestEngine.addTripRequest(tripRequest);
    }

    @Override
    public TripRequest getTripRequest(int TripRequestID) {
        return tripRequestEngine.getTripRequest(TripRequestID);
    }

    @Override
    public void deleteTripRequest(TripRequest requestToDelete) {
        tripRequestEngine.deleteTripRequest(requestToDelete);
    }

    @Override
    public List<TripRequest> getAllTripRequests() {
        return tripRequestEngine.getAllTripRequests();
    }

    @Override
    public MatchedTripRequest getMatchedTripRequest(int MatchedTripRequestID) {
        return tripRequestEngine.getMatchedTripRequest(MatchedTripRequestID);
    }

    @Override
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        tripRequestEngine.addMatchedRequest(matchedTripRequest);
    }

    @Override
    public List<MatchedTripRequest> getAllMatchedTripRequests() {
        return tripRequestEngine.getAllMatchedTripRequests();
    }

    @Override
    public int getNumOfTripRequests() {
        return tripRequestEngine.getNumOfTripRequests();
    }

    @Override
    public int getNumOfMatchedRequests() {
        return tripRequestEngine.getNumOfMatchedRequests();
    }

    //Map-----------------------------------------------------------------------------------//
    @Override
    public int getMapWidth() {
        return map.getMapWidth();
    }

    @Override
    public int getMapLength() {
        return map.getMapLength();
    }

    @Override
    public boolean containsStop(String stopName) {
        return map.containsStop(stopName);
    }

    @Override
    public Map<String, Stop> getAllStops() {
        return map.getAllStops();
    }

    @Override
    public List<Stop> getAllStopsAsList() {
        return map.getAllStopsAsList();
    }

    @Override
    public List<String> getAllStopNamesAsList() {
        return map.getAllStopNamesAsList();
    }

    @Override
    public Stop getStop(String stopName) {
        return map.getStop(stopName);
    }

    @Override
    public int getNumberOfStops() {
        return map.getNumberOfStops();
    }

    @Override
    public List<Path> getAllPaths() {
        return map.getAllPaths();
    }

    @Override
    public boolean containsPath(String source, String destination) {
        return map.containsPath(source, destination);
    }

    @Override
    public Path getPath(Stop source, Stop destination) {
        return map.getPath(source, destination);
    }

    @Override
    public Path getPath(String source, String destination) {
        return map.getPath(source, destination);
    }

    @Override
    public void incrementTime(TimeInterval interval) {
        timeEngine.incrementTime(interval, this);
    }

    @Override
    public void decrementTime(TimeInterval interval) {
        timeEngine.decrementTime(interval, this);
    }

    //TimeEngine---------------------------------------------------------------------------------------//
    @Override
    public TimeDay getCurrentTime() {
        return TimeEngineBase.currentTime;
    }

    @Override
    public int getNumberOfPaths() {
        return map.getNumberOfPaths();
    }

    @Override
    public void incrementTime(TimeInterval interval, Updatable updatable) {
        timeEngine.incrementTime(interval, updatable);
    }

    @Override
    public void decrementTime(TimeInterval interval, Updatable updatable) {
        timeEngine.decrementTime(interval, updatable);
    }

}
