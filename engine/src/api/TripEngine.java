package api;

import data.transpool.TransPoolData;
import data.transpool.map.Map;
import data.transpool.trip.Time;
import data.transpool.trip.TransPoolTripRequest;
import exception.file.StopNotFoundException;
import exception.time.InvalidTimeException;

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
        if (!Map.containsStop(source)) {
            throw new StopNotFoundException(source);
        }
        if (!Map.containsStop(destination)) {
            throw new StopNotFoundException(destination);
        }
        data.addTransPoolTripRequest(new TransPoolTripRequest(riderName, source, destination,
                        new Time(hour, min), isContinuous));
    }
}
