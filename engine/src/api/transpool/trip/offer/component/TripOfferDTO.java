package api.transpool.trip.offer.component;

import api.transpool.map.component.Stop;
import api.transpool.trip.request.component.MatchedTripRequestPart;

import java.util.List;
import java.util.stream.Collectors;

public class TripOfferDTO {
    private String driverName;
    private String departureTime;
    private String recurrences;
    private String sourceStopName;
    private String destinationStopName;
    private List<String> route;
    private List<String> matchedRequestsDetails;
    private int PPK;
    private double fuelConsumption;

    public TripOfferDTO(TripOffer tripOffer) {
        this.driverName = tripOffer.getTransPoolDriver().getUsername();
        this.departureTime = tripOffer.getDepartureTime().toString();
        this.recurrences = tripOffer.getRecurrences().toString();
        this.sourceStopName = tripOffer.getSourceStop().getName();
        this.destinationStopName = tripOffer.getDestinationStop().getName();
        this.PPK = tripOffer.getPPK();
        this.fuelConsumption = tripOffer.getAverageFuelConsumption();
        this.matchedRequestsDetails = initMatchedRequestDetails(tripOffer);
        this.route = initRoute(tripOffer);
    }


    private List<String> initRoute(TripOffer tripOffer) {
        List<String> route = tripOffer
                .getRoute()
                .stream()
                .map(TripOfferPart::getSourceStop)
                .map(Stop::getName)
                .collect(Collectors.toList());
        route.add(tripOffer.getDestinationStop().getName());
        return route;
    }

    private List<String> initMatchedRequestDetails(TripOffer tripOffer) {
        return tripOffer
                .getMatchedRequestsDetails()
                .stream()
                .map(MatchedTripRequestPart::toString)
                .collect(Collectors.toList());
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getSourceStopName() {
        return sourceStopName;
    }

    public String getDestinationStopName() {
        return destinationStopName;
    }

    public List<String> getMatchedRequestsDetails() {
        return matchedRequestsDetails;
    }

    public int getPPK() {
        return PPK;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public String getRecurrences() {
        return recurrences;
    }

    public List<String> getRoute() {
        return route;
    }
}
