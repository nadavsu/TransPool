package data.transpool.trip.offer.data;

import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.time.Recurrence;
import data.transpool.trip.offer.graph.SubTripOffer;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import exception.TransPoolRunTimeException;
import exception.data.PathDoesNotExistException;
import exception.data.TransPoolDataException;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.*;

/**
 * Contains the data for a TransPool trip offered by TransPool drivers.
 */
public class TripOfferData extends BasicTripOfferData implements TripOffer {

    private List<SubTripOffer> route;

    private ObservableList<BasicTripRequest> allMatchedRequestsData;
    private Map<Stop, LocalTime> timeTable;
    private List<Path> usedPaths;

    public TripOfferData(BasicMap map, String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences, int passengerCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        super(driverName, departureTime, dayStart, recurrences, PPK, passengerCapacity);
        this.route = new ArrayList<>();
        this.timeTable = new HashMap<>();
        this.usedPaths = new ArrayList<>();
        this.allMatchedRequestsData = FXCollections.observableArrayList();

        initializeUsedPaths(route, map);

        this.tripPrice.set(calculatePriceOfRoute(this.PPK.get()));
        this.tripDurationInMinutes.set(calculateTripDuration());
        this.averageFuelConsumption.set(calculateAverageFuelConsumption());

        //Order matters.
        initializeTimeTable();
        initializeSubTripOffers();
    }

    public TripOfferData(data.jaxb.TransPoolTrip JAXBTransPoolTrip, BasicMap map) throws TransPoolDataException {
        super(JAXBTransPoolTrip);
        this.route = new ArrayList<>();
        this.usedPaths = new ArrayList<>();
        this.timeTable = new HashMap<>();
        this.allMatchedRequestsData = FXCollections.observableArrayList();

        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        List<String> JAXBRoute = Arrays.asList(routeArray);
        initializeUsedPaths(JAXBRoute, map);

        this.tripPrice.set(calculatePriceOfRoute(this.PPK.get()));
        this.tripDurationInMinutes.set(calculateTripDuration());
        this.averageFuelConsumption.set(calculateAverageFuelConsumption());

        //Order matters.
        initializeTimeTable();
        initializeSubTripOffers();
    }

    private void initializeSubTripOffers() {
        for (int i = 0; i < usedPaths.size(); i++) {
            route.add(new SubTripOffer(i, usedPaths.get(i), this));
        }
    }

    private void initializeTimeTable() {
        LocalTime timeAtStop = getScheduling().getDepartureTime().getTime();
        Path firstPath = usedPaths.get(0);

        timeTable.put(firstPath.getSourceStop(), timeAtStop);
        timeAtStop = timeAtStop.plusMinutes(firstPath.getPathTime());

        for (Path path : usedPaths) {
            timeTable.put(path.getDestinationStop(), timeAtStop);
            timeAtStop = timeAtStop.plusMinutes(path.getPathTime());
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
    public SubTripOffer getSubTripOffer(int ID) {
        return route
                .stream()
                .filter(subTripOffer -> subTripOffer.getSubTripOfferID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCurrentlyHappening() {
        for (SubTripOffer subTripOffer : route) {
            if (subTripOffer.isCurrentlyHappening()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Not used.
     * @param matchedRequest
     */
    @Override
    public void updateAfterMatch(MatchedTripRequest matchedRequest) {
        if (getMaxPassengerCapacity() == 0) {
            throw new TransPoolRunTimeException();
        }
        setMaxPassengerCapacity(getMaxPassengerCapacity() - 1);
        allMatchedRequestsData.add(matchedRequest);
    }

    @Override
    public ObservableList<String> getRouteAsStopsList() {
        ObservableList<String> stopNamesList = FXCollections.observableArrayList();
        stopNamesList.add(route.get(0).getSourceStop().getName());
        route.forEach(subTripOffer -> stopNamesList.add(subTripOffer.getDestinationStop().getName()));
        return stopNamesList;
    }

    @Override
    public double getAverageRating() {
        return transpoolDriver.get().getAverageRating();
    }

    @Override
    public DoubleProperty averageRatingProperty() {
        return transpoolDriver.get().averageRatingProperty();
    }

    @Override
    public ObservableList<BasicTripRequest> getAllMatchedRequestsData() {
        return allMatchedRequestsData;
    }

    @Override
    public void setAllMatchedRequestsData(ObservableList<BasicTripRequest> allMatchedRequestsData) {
        this.allMatchedRequestsData = allMatchedRequestsData;
    }

    @Override
    public List<SubTripOffer> getRoute() {
        return route;
    }

    @Override
    public Map<Stop, LocalTime> getTimeTable() {
        return timeTable;
    }

    @Override
    public List<Path> getUsedPaths() {
        return usedPaths;
    }

    @Override
    public LocalTime getDepartureTimeAtStop(Stop stop) {
        return timeTable.get(stop);
    }

    @Override
    public String toString() {
        return transpoolDriver.get() + " - " + offerID.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripOfferData)) return false;
        TripOfferData that = (TripOfferData) o;
        return offerID.get() == that.offerID.get();
    }
    @Override
    public int hashCode() {
        return Objects.hash(offerID.get());
    }
}
