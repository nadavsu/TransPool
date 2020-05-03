package api;

import data.jaxb.TransPool;
import data.transpool.*;
import data.transpool.structures.TransPoolMap;
import data.transpool.structures.TransPoolTripRequests;
import data.transpool.structures.TransPoolTrips;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;
import exceptions.file.TransPoolFileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Engine {

    private List<TransPoolTrip> possibleMatches;
    private TransPoolTripRequest tripRequestToMatch;

    private static TransPoolData data;
    private static final String JAXB_XML_PACKAGE_NAME = "data.jaxb";

    /*
    Idea: Make a main engine which is abstract and have other mini engines inherit from it such as FileLoaderEngine,
          MatchFinderEngine, TripRequestEngine - have components added to the engine each time. it will be easier to add
          more functionality later this way.

          The member in the abstract engine will be the data.

          Also, you could make another abstract engine which works on a specific trip, FindAMatch will inherit from it.
     */

    public Engine() {
    }

    public TransPoolData getData() {
        return data;
    }

    public List<TransPoolTrip> getPossibleMatches() {
        return possibleMatches;
    }

    public TransPoolTrip getPossibleMatch(int index) {
        return possibleMatches.get(index);
    }

    public TransPoolTrips getAllTransPoolTrips() {
        return TransPoolData.getAllTransPoolTrips();
    }

    public TransPoolTripRequests getAllTransPoolTripRequests() {
        return TransPoolData.getAllTransPoolTripRequests();
    }

    public void loadFile(String fileName) throws JAXBException, TransPoolFileNotFoundException,
            TransPoolDataException, InvalidTimeException {
        InputStream istream =  Engine.class.getResourceAsStream("/resources/" + fileName);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);
        data = new TransPoolData(JAXBData);

    }

    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              int hour, int min, boolean isContinuous) throws InvalidTimeException,
                                              StopNotFoundException {
        if (!TransPoolMap.getAllStops().containsName(source)) {
            throw new StopNotFoundException(source);
        }
        if (!TransPoolMap.getAllStops().containsName(destination)) {
            throw new StopNotFoundException(destination);
        }
        TransPoolData
                .getAllTransPoolTripRequests()
                .addTransPoolTripRequest(new TransPoolTripRequest(riderName, source, destination,
                                         new Time(hour, min), isContinuous));
    }

    public void findPossibleMatches(int tripID, int maximumMatches) {
        tripRequestToMatch = TransPoolData.getAllTransPoolTripRequests().getTripRequestByID(tripID);

        Predicate<TransPoolTrip> containsSubRoutePredicate = transPoolTrip ->
                transPoolTrip.containsSubRoute(tripRequestToMatch.getSource(), tripRequestToMatch.getDestination());

        Predicate<TransPoolTrip> timeMatchPredicate = transPoolTrip ->
                transPoolTrip.getSchedule().getTime().equals(tripRequestToMatch.getTimeOfDeparture());

        possibleMatches = TransPoolData
                .getAllTransPoolTrips()
                .getTranspoolTrips()
                .stream()
                .filter(containsSubRoutePredicate)
                .filter(timeMatchPredicate)
                .limit(maximumMatches)
                .collect(Collectors.toList());
    }

    public void createNewMatch(int index) throws StopNotFoundException {
        TransPoolTrip chosenTransPoolTrip = possibleMatches.get(index);
        data.addMatch(new MatchedTransPoolTripRequest(tripRequestToMatch, chosenTransPoolTrip));
    }

    public void _debugfill() throws InvalidTimeException, StopNotFoundException, TransPoolDataException, JAXBException, TransPoolFileNotFoundException {
        loadFile("ex1-real.xml");
        createNewTransPoolTripRequest("Nadav", "MTA", "Bursa", 17, 0, true);
        createNewTransPoolTripRequest("Lasri", "Park Hayarkon", "Wolfson train", 6, 0, true);
        createNewTransPoolTripRequest("Shaide", "Lagardia", "HIT", 22, 0, true);
        createNewTransPoolTripRequest("Rami", "HIT", "MTA", 7, 0, true);
    }
}
