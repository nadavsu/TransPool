package api.transpool;

import api.generated.TransPool;
import api.transpool.map.BasicMap;
import api.transpool.map.BasicMapDTO;
import api.transpool.map.BasicMapData;
import api.transpool.map.component.Path;
import api.transpool.map.component.PathDTO;
import api.transpool.map.component.Stop;
import api.transpool.map.component.StopDTO;
import api.transpool.time.component.TimeDay;
import api.transpool.time.TimeEngineBase;
import api.transpool.time.component.TimeInterval;
import api.transpool.time.component.Updatable;
import api.transpool.trip.matching.component.TripOffersGraph;
import api.transpool.trip.offer.TripOffersEngineBase;
import api.transpool.trip.offer.component.TripOfferDTO;
import api.transpool.trip.offer.component.TripOfferPart;
import api.transpool.trip.matching.component.PossibleRoute;
import api.transpool.trip.matching.component.PossibleRoutesList;
import api.transpool.trip.offer.component.TripOffer;
import api.transpool.trip.request.TripRequestsEngineBase;
import api.transpool.trip.request.component.MatchedTripRequest;
import api.transpool.trip.request.component.MatchedTripRequestDTO;
import api.transpool.trip.request.component.TripRequest;
import api.transpool.trip.request.component.TripRequestDTO;
import exception.parser.NoResultsFoundException;
import exception.parser.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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

    private BasicMapData map;
    private TripOffersEngineBase tripOffersEngine;
    private TripRequestsEngineBase tripRequestsEngine;
    private TimeEngineBase timeEngine;

    private List<Updatable> updatables;

    public TransPoolMap(String mapName, String uploaderName, TransPool JAXBData) throws TransPoolDataException {
        Stop.resetIDGenerator();

        this.mapName = mapName;
        this.uploaderName = uploaderName;

        this.map = new BasicMapData(JAXBData.getMapDescriptor());
        this.tripRequestsEngine = new TripRequestsEngineBase();
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
    public synchronized SingleMapEngineDTO getMapEngineDetails() {
        return new SingleMapEngineDTO(this);
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
    public synchronized List<TripOfferDTO> getTripOffersDetails() {
        return tripOffersEngine.getTripOffersDetails();
    }

    @Override
    public synchronized void addTripOffer(TripOffer tripOffer) {
        tripOffersEngine.addTripOffer(tripOffer);
    }

    @Override
    public synchronized List<TripOffer> getAllTripOffers() {
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
    public TripOfferPart getTripOfferPart(int tripOfferID, int subTripOfferID) {
        return tripOffersEngine.getTripOfferPart(tripOfferID, subTripOfferID);
    }

    @Override
    public TripOffersGraph getTripOffersGraph() {
        return tripOffersEngine.getTripOffersGraph();
    }

    //TripRequestEngine------------------------------------------------------------//

    @Override
    public synchronized List<TripRequestDTO> getTripRequestsDetails() {
        return tripRequestsEngine.getTripRequestsDetails();
    }

    @Override
    public List<MatchedTripRequestDTO> getMatchedTripsDetails() {
        return tripRequestsEngine.getMatchedTripsDetails();
    }

    @Override
    public synchronized void addTripRequest(TripRequest tripRequest) {
        tripRequestsEngine.addTripRequest(tripRequest);
    }

    @Override
    public TripRequest getTripRequest(int TripRequestID) {
        return tripRequestsEngine.getTripRequest(TripRequestID);
    }

    @Override
    public synchronized void deleteTripRequest(TripRequest requestToDelete) {
        tripRequestsEngine.deleteTripRequest(requestToDelete);
    }

    @Override
    public synchronized List<TripRequest> getAllTripRequests() {
        return tripRequestsEngine.getAllTripRequests();
    }

    @Override
    public MatchedTripRequest getMatchedTripRequest(int MatchedTripRequestID) {
        return tripRequestsEngine.getMatchedTripRequest(MatchedTripRequestID);
    }

    @Override
    public synchronized void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        tripRequestsEngine.addMatchedRequest(matchedTripRequest);
    }

    @Override
    public synchronized List<MatchedTripRequest> getAllMatchedTripRequests() {
        return tripRequestsEngine.getAllMatchedTripRequests();
    }

    @Override
    public int getNumOfTripRequests() {
        return tripRequestsEngine.getNumOfTripRequests();
    }

    @Override
    public int getNumOfMatchedRequests() {
        return tripRequestsEngine.getNumOfMatchedRequests();
    }

    //Matching Engine-----------------------------------------------------------------------//

    /**
     * Gets the possible routes from the TripOfferMap, and filters all routes which are not relevant by
     * departure or arrival time. Also filters all rides that are not continuous if the rider asked for continuous rides.
     * @param tripRequestID - The ID of the trip request to match
     * @param maximumMatches - The maximum number of matches.
     * @return - PossibleRoutesList - a list of all possible routes.
     */
    @Override
    public PossibleRoutesList getAllPossibleRoutes(int tripRequestID, int maximumMatches) throws NoResultsFoundException {
        TripRequest requestToMatch = getTripRequest(tripRequestID);
        Predicate<PossibleRoute> timeMatchPredicate = possibleRoute -> {
            if (requestToMatch.isTimeOfArrival()) {
                return possibleRoute.getArrivalTime().equals(requestToMatch.getRequestTime());
            } else {
                return possibleRoute.getDepartureTime().equals(requestToMatch.getRequestTime());
            }
        };
        Predicate<PossibleRoute> continuousRidePredicate = possibleRoute ->
                !requestToMatch.isContinuous() || possibleRoute.isContinuous();

        PossibleRoutesList possibleRoutes = getTripOffersGraph()
                .getAllPossibleRoutes(
                        requestToMatch.getSourceStop(),
                        requestToMatch.getDestinationStop(),
                        requestToMatch.getRequestTime())
                .stream()
                .filter(timeMatchPredicate)
                .filter(continuousRidePredicate)
                .limit(maximumMatches)
                .collect(Collectors.toCollection(PossibleRoutesList::new));

        if (possibleRoutes.isEmpty()) {
            throw new NoResultsFoundException();
        } else {
            return possibleRoutes;
        }

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
    public List<StopDTO> getAllStopsDetails() {
        return map.getAllStopsDetails();
    }

    @Override
    public List<PathDTO> getAllPathsDetails() {
        return map.getAllPathsDetails();
    }

    @Override
    public BasicMapDTO getMapDetails() {
        return map.getMapDetails();
    }

    //TimeEngine---------------------------------------------------------------------------------------//

    @Override
    public void incrementTime(TimeInterval interval) {
        timeEngine.incrementTime(interval, this);
    }

    @Override
    public void decrementTime(TimeInterval interval) {
        timeEngine.decrementTime(interval, this);
    }

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
