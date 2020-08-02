package data.transpool.trip.matching.component;

import data.transpool.trip.offer.component.TripOfferPartOccurrence;

import java.util.List;
import java.util.stream.Collectors;

public class PossibleRouteDTO {

    private List<String> route;
    private int totalPrice;
    private double averageFuelConsumption;
    private int totalTripDuration;
    private String arrivalTime;
    private String departureTime;

    public PossibleRouteDTO(PossibleRoute possibleRoute) {
        this.route = initRoute(possibleRoute.getRoute());
        this.totalPrice = possibleRoute.getTotalPrice();
        this.averageFuelConsumption = possibleRoute.getAverageFuelConsumption();
        this.totalTripDuration = possibleRoute.getTotalTripDuration();
        this.arrivalTime = possibleRoute.getArrivalTime().toString();
        this.departureTime = possibleRoute.getDepartureTime().toString();
    }

    private List<String> initRoute(List<TripOfferPartOccurrence> route) {
        return route
                .stream()
                .map(TripOfferPartOccurrence::getTripOfferOccurrenceDescription)
                .collect(Collectors.toList());
    }

    public List<String> getRoute() {
        return route;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public int getTotalTripDuration() {
        return totalTripDuration;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }
}
