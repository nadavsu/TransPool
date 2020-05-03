package api;

import data.transpool.TransPoolData;
import data.transpool.structures.TransPoolMap;
import data.transpool.trips.Time;
import data.transpool.trips.TransPoolTripRequest;
import exceptions.StopNotFoundException;
import exceptions.time.InvalidTimeException;

public class TripEngine extends Engine {

    public TripEngine() { }

    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              int hour, int min, boolean isContinuous) throws InvalidTimeException,
                                                StopNotFoundException {
        if (!TransPoolMap.getAllStops().containsName(source)) {
            throw new StopNotFoundException(source);
        }
        if (!TransPoolMap.getAllStops().containsName(destination)) {
            throw new StopNotFoundException(destination);
        }
        TransPoolData.addTransPoolTripRequest(new TransPoolTripRequest(riderName, source, destination,
                        new Time(hour, min), isContinuous));
    }

    public void _debugfill() throws InvalidTimeException, StopNotFoundException {
        createNewTransPoolTripRequest("Nadav", "MTA", "Bursa", 17, 0, true);
        createNewTransPoolTripRequest("Lasri", "Park Hayarkon", "Wolfson train", 6, 0, true);
        createNewTransPoolTripRequest("Shaide", "Lagardia", "HIT", 22, 0, true);
        createNewTransPoolTripRequest("Rami", "HIT", "MTA", 7, 0, true);
    }
}
