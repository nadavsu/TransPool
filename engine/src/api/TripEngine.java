package api;

import data.transpool.TransPoolData;
import data.transpool.map.Map;
import data.transpool.trips.Time;
import data.transpool.trips.TransPoolTripRequest;
import exceptions.StopNotFoundException;
import exceptions.time.InvalidTimeException;

public class TripEngine extends Engine {

    public TripEngine() { }

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

    public void _debugfill() throws InvalidTimeException, StopNotFoundException {
        createNewTransPoolTripRequest("Nadav", "Wolfson train", "MTA", 13, 0, true);
        createNewTransPoolTripRequest("Lasri", "Yoseftal", "Wolfson train", 13, 0, true);
        createNewTransPoolTripRequest("Shaide", "MTA", "Rabin Square", 22, 0, true);
        createNewTransPoolTripRequest("Rami", "HIT", "MTA", 7, 0, true);
    }
}
