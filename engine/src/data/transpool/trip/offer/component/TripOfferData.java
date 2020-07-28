package data.transpool.trip.offer.component;

import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.component.Scheduling;
import data.transpool.time.component.TimeDay;
import data.transpool.time.component.Recurrence;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestPart;
import exception.data.PathDoesNotExistException;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.*;

/**
 * Contains the static data for a TransPool trip offered by TransPool drivers.
 */
public class TripOfferData extends BasicTripOfferData implements TripOffer {

    private List<TripOfferPart> route;
    private Map<Stop, TimeDay> timeTable;
    private List<Path> usedPaths;
    private List<MatchedTripRequestPart> matchedRequestsDetails;

    public TripOfferData(BasicMap map, String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences, int passengerCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        super(driverName, PPK, passengerCapacity);
        this.matchedRequestsDetails = FXCollections.observableArrayList();
        this.route = new ArrayList<>();
        this.timeTable = new HashMap<>();
        this.usedPaths = new ArrayList<>();

        initializeUsedPaths(route, map);

        //Order matters.
        this.tripPrice = calculatePriceOfRoute(this.PPK);
        this.tripDurationInMinutes = calculateTripDuration();
        this.averageFuelConsumption = calculateAverageFuelConsumption();
        this.schedule = new Scheduling(dayStart, departureTime, tripDurationInMinutes, recurrences);

        //Order matters.
        initializeTimeTable();
        initializeSubTripOffers();
    }

    private void initializeTimeTable() {
        TimeDay timeAtStop = new TimeDay(getScheduling().getDepartureTime());
        Path firstPath = usedPaths.get(0);

        timeTable.put(firstPath.getSourceStop(), new TimeDay(timeAtStop));
        timeAtStop.plus(firstPath.getPathTime());

        for (Path path : usedPaths) {
            timeTable.put(path.getDestinationStop(), new TimeDay(timeAtStop));
            timeAtStop.plus(path.getPathTime());
        }
    }

    private void initializeSubTripOffers() {
        for (int i = 0; i < usedPaths.size(); i++) {
            route.add(new TripOfferPart(i, usedPaths.get(i), this));
        }
    }


    private void initializeUsedPaths(List<String> route, BasicMap map) throws PathDoesNotExistException {
        for (int i = 0; i < route.size() - 1; i++) {
            Path foundPath = map.getPath(route.get(i).trim(), route.get(i + 1).trim());
            if (foundPath == null) {
                throw new PathDoesNotExistException(route.get(i).trim(), route.get(i + 1).trim());
            }
            usedPaths.add(new Path(foundPath));
        }
    }

    private int calculatePriceOfRoute(int PPK) {
        return this
                .getUsedPaths()
                .stream()
                .mapToInt(p -> p.getLength() * PPK)
                .sum();
    }

    private int calculateTripDuration() {
        return this
                .getUsedPaths()
                .stream()
                .mapToInt(Path::getPathTime)
                .sum();
    }

    private double calculateAverageFuelConsumption() {
        return this
                .getUsedPaths()
                .stream()
                .mapToDouble(Path::getFuelConsumption)
                .average()
                .orElse(0);
    }

    @Override
    public TripOfferPart getSubTripOffer(int ID) {
        return route
                .stream()
                .filter(subTripOffer -> subTripOffer.getSubTripOfferID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCurrentlyHappening() {
        for (TripOfferPart tripOfferPart : route) {
            if (tripOfferPart.isCurrentlyHappening()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TimeDay getDepartureTime() {
        return getTimeAtStop(getSourceStop());
    }

    @Override
    public TimeDay getArrivalTime() {
        return route.get(route.size() - 1).getTimeOfArrivalAtDestination();
    }

    @Override
    public TripOfferPart getCurrentSubTripOffer() {
        for (TripOfferPart tripOfferPart : route) {
            if (tripOfferPart.isCurrentlyDeparting()) {
                return tripOfferPart;
            }
        }
        if (route.get(route.size() - 1).isCurrentlyArriving()) {
            return route.get(route.size() - 1);
        }
        return null;
    }

    @Override
    public Stop getSourceStop() {
        return route.get(0).getSourceStop();
    }

    @Override
    public Stop getDestinationStop() {
        return route.get(route.size() - 1).getDestinationStop();
    }

    @Override
    public TimeDay getTimeAtStop(Stop stop) {
        return timeTable.get(stop);
    }

    @Override
    public List<MatchedTripRequestPart> getMatchedRequestsDetails() {
        return matchedRequestsDetails;
    }

    @Override
    public void updateAfterMatch(MatchedTripRequest matchedRequest, TimedSubTripOffer subTripOffer) {
        matchedRequestsDetails.add(new MatchedTripRequestPart(matchedRequest.getTransPoolRider(), subTripOffer));
    }

    @Override
    public List<TripOfferPart> getRoute() {
        return route;
    }

    @Override
    public Map<Stop, TimeDay> getTimeTable() {
        return timeTable;
    }

    @Override
    public List<Path> getUsedPaths() {
        return usedPaths;
    }

    @Override
    public String toString() {
        return transpoolDriver + " - " + offerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripOfferData)) return false;
        TripOfferData that = (TripOfferData) o;
        return this.offerID == that.offerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerID);
    }
}