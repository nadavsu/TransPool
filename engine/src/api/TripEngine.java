package api;

import data.transpool.TransPoolData;
import data.transpool.map.Map;
import data.transpool.trips.Time;
import data.transpool.trips.TransPoolTripRequest;
import exceptions.StopNotFoundException;
import exceptions.time.InvalidTimeException;

/**
 * An engine for working on a specific trip, or creating a new trip request.
 */
public class TripEngine extends Engine {

    public TripEngine() { }

    /**
     * Creates a new trip request using the given parameters.
     * @param riderName - The name of the requester.
     * @param source - Name of stop the requester gets on.
     * @param destination - Name of stop the requester gets off.
     * @param hour - Hour of the request.
     * @param min - Minute of the request.
     * @param isContinuous - true if the requester would like to get on a single continuous ride.
     * @throws InvalidTimeException - If time is not valid.
     * @throws StopNotFoundException - If the desired destination or source do not exist.
     */
    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              int hour, int min, boolean isContinuous) throws InvalidTimeException,
                                                StopNotFoundException {
        if (!Map.getAllStops().containsName(source)) {
            throw new StopNotFoundException(source);
        }
        if (!Map.getAllStops().containsName(destination)) {
            throw new StopNotFoundException(destination);
        }
        TransPoolData.addTransPoolTripRequest(new TransPoolTripRequest(riderName, source, destination,
                        new Time(hour, min), isContinuous));
    }

    //TODO: delete this!
    public void _debugfill() throws InvalidTimeException, StopNotFoundException {
        createNewTransPoolTripRequest("Nadav", "Wolfson train", "MTA", 13, 0, true);
        createNewTransPoolTripRequest("Lasri", "Yoseftal", "Wolfson train", 13, 0, true);
        createNewTransPoolTripRequest("Shaide", "MTA", "Rabin Square", 22, 0, true);
        createNewTransPoolTripRequest("Rami", "HIT", "MTA", 7, 0, true);
    }
}
