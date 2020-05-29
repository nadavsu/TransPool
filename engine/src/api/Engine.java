package api;

import data.transpool.TransPoolData;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import exception.NoMatchesFoundException;
import exception.file.StopNotFoundException;
import exception.file.TransPoolDataException;
import exception.file.TransPoolFileNotFoundException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalTime;
import java.util.List;

public interface Engine {

    void loadFile(File file) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException;

    void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                       LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws StopNotFoundException;

    List<String> getAllTransPoolTripRequestsAsStrings();

    List<TransPoolTripRequest> getAllTransPoolTripRequests();

    List<TransPoolTrip> getAllTransPoolTrips();

    void findPossibleMatches(int tripRequestID, int maximumMatches) throws NoMatchesFoundException;

    List<PossibleMatch> getPossibleMatches();

    void addNewMatch(int indexOfPossibleMatchesList);

    TransPoolData getData();
}
