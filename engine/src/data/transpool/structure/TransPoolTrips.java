package data.transpool.structure;

import data.jaxb.PlannedTrips;
import data.transpool.trip.TransPoolTrip;
import exception.file.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolTrips {
    private List<TransPoolTrip> transpoolTrips = new ArrayList<>();

    public TransPoolTrips(PlannedTrips JAXBTransPoolTrips) throws TransPoolDataException {
        List<data.jaxb.TransPoolTrip> JAXBTrips = JAXBTransPoolTrips.getTransPoolTrip();
        for (data.jaxb.TransPoolTrip JAXBTrip : JAXBTrips) {
            addTransPoolTrip(new TransPoolTrip(JAXBTrip));
        }
    }

    /**
     * Adds a TP trip to the structure.
     * @param transpoolTrip - the trip to add.
     */
    private void addTransPoolTrip(TransPoolTrip transpoolTrip) {
        /*Route tripRoute = transpoolTrip.getRoute();
        for (int i = 0; i <  tripRoute.getNumberOfStops() - 1; i++) {
            String source = tripRoute.getStopNameByIndex(i);
            String destination = tripRoute.getStopNameByIndex(i + 1);
            if (!Map.getAllPaths().containsPath(source, destination)) {
                throw new PathDoesNotExistException(source, destination);
            }
        }*/
        transpoolTrips.add(transpoolTrip);
    }

    public TransPoolTrip getTransPoolTripByID(int ID) {
        return transpoolTrips
                .stream()
                .filter(t -> t.getID() == ID)
                .findFirst()
                .orElse(null);
    }

    public List<TransPoolTrip> getTranspoolTrips() {
        return transpoolTrips;
    }

    @Override
    public String toString() {
        StringBuilder transPoolTripsString = new StringBuilder();
        for (TransPoolTrip trip : transpoolTrips) {
            transPoolTripsString.append(trip.toString());
            transPoolTripsString.append("\n");
        }
        return transPoolTripsString.toString();
    }
}
