package data.transpool.trips;

import data.transpool.map.TransPoolPath;
import exceptions.StopNotFoundException;

import java.util.List;

public class PossibleMatch {

    private int transpoolTripID;
    private String transpoolDriverName;
    private int tripPrice;
    private Time estimatedTimeOfArrival;
    private double averageFuelConsumption;

    private TransPoolTrip possibleTransPoolTrip;

    public PossibleMatch(TransPoolTripRequest transpoolTripRequest, TransPoolTrip possibleTransPoolTrip) {
        this.possibleTransPoolTrip = possibleTransPoolTrip;
        this.transpoolTripID = possibleTransPoolTrip.getID();
        this.transpoolDriverName = possibleTransPoolTrip.getOwner().getUsername();
        calculateTripPrice(transpoolTripRequest, possibleTransPoolTrip);
        calculateEstimatedTimeOfArrival(transpoolTripRequest, possibleTransPoolTrip);
        calculateAverageFuelConsumption(transpoolTripRequest, possibleTransPoolTrip);
    }

    private void calculateTripPrice(TransPoolTripRequest transpoolTripRequest, TransPoolTrip possibleTransPoolTrip) throws StopNotFoundException {
        List<TransPoolPath> subRoutePath = possibleTransPoolTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(), transpoolTripRequest.getDestination());

        tripPrice = subRoutePath
                .stream()
                .mapToInt(p -> p.getLength() * possibleTransPoolTrip.getPPK())
                .sum();
    }

    private void calculateAverageFuelConsumption(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) throws StopNotFoundException {
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());
        averageFuelConsumption = subRoutePath
                .stream()
                .mapToDouble(TransPoolPath::getFuelConsumption)
                .sum();
    }

    private void calculateEstimatedTimeOfArrival(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) throws StopNotFoundException {
        estimatedTimeOfArrival = transpoolTripRequest.getTimeOfDeparture();
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());
        int minutesToAdd = subRoutePath
                .stream()
                .mapToInt(TransPoolPath::getPathTime)
                .sum();
        estimatedTimeOfArrival.addMinutes(minutesToAdd);
    }

    public int getTranspoolTripID() {
        return transpoolTripID;
    }

    public String getTranspoolDriverName() {
        return transpoolDriverName;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public Time getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public TransPoolTrip getPossibleTransPoolTrip() {
        return possibleTransPoolTrip;
    }

    @Override
    public String toString() {
        return "TransPool Trip ID: " + transpoolTripID + "\n"
                + "Driver name: " + transpoolDriverName + "\n"
                + "Estimated time of arrival: " + estimatedTimeOfArrival + "\n"
                + "Average fuel consumption: " + averageFuelConsumption + "\n"
                + "Trip price: " + tripPrice + "\n";

    }
}
