package data.transpool.trip;

import data.transpool.map.Path;
import data.transpool.user.TransPoolDriver;

import java.util.List;

public class PossibleMatch {

    private int transpoolTripID;
    private TransPoolDriver transpoolDriver;
    private int tripPrice;
    private Time estimatedTimeOfArrival;
    private double averageFuelConsumption;

    private TransPoolTrip possibleTransPoolTrip;

    public PossibleMatch(TransPoolTripRequest transpoolTripRequest, TransPoolTrip possibleTransPoolTrip) {
        this.possibleTransPoolTrip = possibleTransPoolTrip;
        this.transpoolTripID = possibleTransPoolTrip.getID();
        this.transpoolDriver = possibleTransPoolTrip.getOwner();
        this.estimatedTimeOfArrival = new Time(possibleTransPoolTrip.getSchedule().getTime());

        calculateTripPrice(transpoolTripRequest, possibleTransPoolTrip);
        calculateEstimatedTimeOfArrival(transpoolTripRequest, possibleTransPoolTrip);
        calculateAverageFuelConsumption(transpoolTripRequest, possibleTransPoolTrip);
    }

    private void calculateTripPrice(TransPoolTripRequest transpoolTripRequest, TransPoolTrip possibleTransPoolTrip) {
        List<Path> subRoutePath = possibleTransPoolTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(), transpoolTripRequest.getDestination());

        tripPrice = subRoutePath
                .stream()
                .mapToInt(p -> p.getLength() * possibleTransPoolTrip.getPPK())
                .sum();
    }

    private void calculateAverageFuelConsumption(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) {
        List<Path> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());

        averageFuelConsumption = subRoutePath
                .stream()
                .mapToDouble(Path::getFuelConsumption)
                .sum();
    }

    private void calculateEstimatedTimeOfArrival(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) {
        List<Path> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());

        int minutesToAdd = subRoutePath
                .stream()
                .mapToInt(Path::getPathTime)
                .sum();
        estimatedTimeOfArrival.addMinutes(minutesToAdd);
    }

    public int getTranspoolTripID() {
        return transpoolTripID;
    }

    public TransPoolDriver getTranspoolDriverName() {
        return transpoolDriver;
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
        return "+-----------------TransPool Trip-----------------+"
                + "TransPool Trip ID:                       " + transpoolTripID + "\n"
                + "Driver name:                             " + transpoolDriver + "\n"
                + "Estimated time of arrival:               " + estimatedTimeOfArrival + "\n"
                + "Average fuel consumption:                " + averageFuelConsumption + "\n"
                + "Trip price:                              " + tripPrice + "\n";

    }
}
