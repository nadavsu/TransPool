package data.transpool.trip.request.component;

import data.transpool.time.component.TimeDay;
import data.transpool.trip.offer.component.TripOfferPartOccurrence;

import java.util.List;
import java.util.stream.Collectors;

public class MatchedTripRequestDTO {
    private String riderName;
    private String sourceStopName;
    private String destinationStopName;
    private String departureTime;
    private String arrivalTime;
    private List<String> routeDescription;
    private int tripPrice;
    private double averageFuelConsumption;

    public MatchedTripRequestDTO(MatchedTripRequest matchedTripRequest) {
        this.riderName = matchedTripRequest.getTransPoolRider().getUsername();
        this.sourceStopName = matchedTripRequest.getSourceStop().getName();
        this.destinationStopName = matchedTripRequest.getDestinationStop().getName();
        this.departureTime = matchedTripRequest.getTimeOfDeparture().toString();
        this.arrivalTime = matchedTripRequest.getExpectedTimeOfArrival().toString();
        this.tripPrice = matchedTripRequest.getTripPrice();
        this.averageFuelConsumption = matchedTripRequest.getPersonalFuelConsumption();
        this.routeDescription = initRouteDescription(matchedTripRequest);
    }

    private List<String> initRouteDescription(MatchedTripRequest matchedTripRequest) {
        return matchedTripRequest
                .getRoute()
                .stream()
                .map(TripOfferPartOccurrence::getTripOfferOccurrenceDescription).collect(Collectors.toList());
    }

    public String getRiderName() {
        return riderName;
    }

    public String getSourceStopName() {
        return sourceStopName;
    }

    public String getDestinationStopName() {
        return destinationStopName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public List<String> getRouteDescription() {
        return routeDescription;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }
}
