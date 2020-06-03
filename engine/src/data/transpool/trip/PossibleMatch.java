package data.transpool.trip;

import data.transpool.map.Path;
import data.transpool.user.TransPoolDriver;

import java.time.LocalTime;
import java.util.List;

public class PossibleMatch {

    private int transpoolTripID;
    private TransPoolDriver transpoolDriver;
    private int tripPrice;
    private LocalTime estimatedTimeOfArrival;
    private double averageFuelConsumption;

    private TransPoolTrip possibleTransPoolTrip;

    public PossibleMatch(TransPoolTripRequest transpoolTripRequest, TransPoolTrip possibleTransPoolTrip) {
        this.possibleTransPoolTrip = possibleTransPoolTrip;
        this.transpoolTripID = possibleTransPoolTrip.getOfferID();
        this.transpoolDriver = possibleTransPoolTrip.getTranspoolDriver();
        this.estimatedTimeOfArrival = LocalTime.of(possibleTransPoolTrip.getSchedule().getTime().getHour(),
                possibleTransPoolTrip.getSchedule().getTime().getMinute());

        calculateTripPrice(transpoolTripRequest, possibleTransPoolTrip);
        calculateEstimatedTimeOfArrival(transpoolTripRequest, possibleTransPoolTrip);
        calculateAverageFuelConsumption(transpoolTripRequest, possibleTransPoolTrip);
    }

    private void calculateTripPrice(TransPoolTripRequest transpoolTripRequest, TransPoolTrip possibleTransPoolTrip) {
        List<Path> subRoutePath = possibleTransPoolTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSourceStop(), transpoolTripRequest.getDestinationStop());

        tripPrice = subRoutePath
                .stream()
                .mapToInt(p -> p.getLength() * possibleTransPoolTrip.getPPK())
                .sum();
    }

    private void calculateAverageFuelConsumption(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) {
        List<Path> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSourceStop(),transpoolTripRequest.getDestinationStop());

        averageFuelConsumption = subRoutePath
                .stream()
                .mapToDouble(Path::getFuelConsumption)
                .sum();
    }

    private void calculateEstimatedTimeOfArrival(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) {
        List<Path> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSourceStop(),transpoolTripRequest.getDestinationStop());

        int minutesToAdd = subRoutePath
                .stream()
                .mapToInt(Path::getPathTime)
                .sum();
        estimatedTimeOfArrival = estimatedTimeOfArrival.plusMinutes(minutesToAdd);
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

    public LocalTime getEstimatedTimeOfArrival() {
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
        return "+-----------------TransPool Trip-----------------+\n"
                + "TransPool Trip ID:                       " + transpoolTripID + "\n"
                + "Driver name:                             " + transpoolDriver + "\n"
                + "Estimated time of arrival:               " + estimatedTimeOfArrival + "\n"
                + "Average fuel consumption:                " + averageFuelConsumption + "\n"
                + "Trip price:                              " + tripPrice + "\n";

    }
}
