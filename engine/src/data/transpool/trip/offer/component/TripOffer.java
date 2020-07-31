package data.transpool.trip.offer.component;

import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.time.component.Recurrence;
import data.transpool.trip.request.component.MatchedTripRequest;
import data.transpool.trip.request.component.MatchedTripRequestPart;
import data.transpool.user.account.Rider;
import data.transpool.user.account.TransPoolDriver;
import data.transpool.user.account.TransPoolRider;
import exception.data.PathDoesNotExistException;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;

import java.time.LocalTime;
import java.util.*;

/**
 * Contains the static data for a TransPool trip offered by TransPool drivers.
 */
public class TripOffer implements SingleTripOfferEngine, BasicTripOffer {

    private static int IDGenerator = 10000000;

    protected int ID;
    private TransPoolDriver transpoolDriver;
    private int PPK;
    private int maxPassengerCapacity;
    private int tripPrice;
    private double averageFuelConsumption;
    private int tripDurationInMinutes;

    private TimeDay departureTime;
    private Recurrence recurrences;

    private List<TripOfferPart> route;
    private Map<Stop, TimeDay> timeTable;
    private List<Path> usedPaths;
    private List<MatchedTripRequestPart> matchedRequestsDetails;

    public TripOffer(BasicMap map, TransPoolDriver driver, LocalTime departureTime, int dayStart, Recurrence recurrences, int passengerCapacity, int PPK, List<String> route) throws TransPoolDataException {
        this.ID = IDGenerator;
        IDGenerator += 1000;

        this.transpoolDriver = driver;
        this.maxPassengerCapacity = passengerCapacity;
        this.PPK = PPK;

        this.departureTime = new TimeDay(departureTime, dayStart);
        this.recurrences = recurrences;

        this.matchedRequestsDetails = new ArrayList<>();
        this.route = new ArrayList<>();
        this.timeTable = new HashMap<>();
        this.usedPaths = new ArrayList<>();

        initializeUsedPaths(route, map);

        //Order matters.
        this.tripPrice = calculatePriceOfRoute(this.PPK);
        this.tripDurationInMinutes = calculateTripDuration();
        this.averageFuelConsumption = calculateAverageFuelConsumption();
        //this.schedule = new Scheduling(dayStart, departureTime, tripDurationInMinutes, recurrences);

        //Order matters.
        initializeTimeTable();
        initializeTripOfferParts();
    }

    private void initializeTimeTable() {
        TimeDay timeAtStop = new TimeDay(departureTime);
        Path firstPath = usedPaths.get(0);

        timeTable.put(firstPath.getSourceStop(), new TimeDay(timeAtStop));
        timeAtStop.plus(firstPath.getPathTime());

        for (Path path : usedPaths) {
            timeTable.put(path.getDestinationStop(), new TimeDay(timeAtStop));
            timeAtStop.plus(path.getPathTime());
        }
    }

    private void initializeTripOfferParts() {
        for (int i = 0; i < usedPaths.size(); i++) {
            route.add(new TripOfferPart(ID + 100*i, usedPaths.get(i), this));
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

    public TripOfferDTO getDetails() {
        return new TripOfferDTO(this);
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public TransPoolDriver getTransPoolDriver() {
        return transpoolDriver;
    }

    @Override
    public int getPPK() {
        return PPK;
    }

    @Override
    public int getTripDurationInMinutes() {
        return tripDurationInMinutes;
    }

    @Override
    public int getPrice() {
        return tripPrice;
    }

    @Override
    public Recurrence getRecurrences() {
        return recurrences;
    }

    @Override
    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    @Override
    public int getMaxPassengerCapacity() {
        return maxPassengerCapacity;
    }

    @Override
    public TripOfferPart getSubTripOffer(int ID) {
        return route
                .stream()
                .filter(subTripOffer -> subTripOffer.getID() == ID)
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
        return route.get(route.size() - 1).getArrivalTime();
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
    public void updateAfterMatch(TransPoolRider rider, TripOfferPartOccurrence subTripOffer) {
        matchedRequestsDetails.add(new MatchedTripRequestPart(rider, subTripOffer));
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
        return transpoolDriver + " - " + ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripOffer)) return false;
        TripOffer that = (TripOffer) o;
        return this.ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}