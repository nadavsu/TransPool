package api;

import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import exception.file.StopNotFoundException;
import exception.file.TransPoolDataException;
import exception.file.TransPoolFileNotFoundException;
import exception.time.InvalidTimeException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public interface Engine {

    void loadFile(File file) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException;

    void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                       int hour, int min, boolean isContinuous)
            throws InvalidTimeException, StopNotFoundException;

    List<String> getAllTransPoolTripRequestsAsStrings();

    List<TransPoolTripRequest> getAllTransPoolTripRequests();

    List<TransPoolTrip> getAllTransPoolTrips();

    void findPossibleMatches(int tripRequestID, int maximumMatches) throws NoMatchesFoundException;

    List<PossibleMatch> getPossibleMatches();

    void addNewMatch(int indexOfPossibleMatchesList);
}
