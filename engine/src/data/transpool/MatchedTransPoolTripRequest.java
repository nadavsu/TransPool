package data.transpool;

import java.util.List;

public class MatchedTransPoolTripRequest implements TripRequest {

    private TransPoolTripRequest transpoolTripRequest;
    private TransPoolTrip matchedTrip;

    private double personalFuelConsumption;
    private int tripPrice;
    private Time expectedTimeOfArrival;

    public MatchedTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest, TransPoolTrip matchedTrip) {
        this.transpoolTripRequest = transpoolTripRequest;
        this.matchedTrip = matchedTrip;
        calculateTripPrice();
        calculatePersonalFuelConsumption();
        calculateExpectedTimeOfArrival();
    }

    private void calculateTripPrice() {
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(), transpoolTripRequest.getDestination());
        tripPrice = subRoutePath
                .stream()
                .mapToInt(p -> p.getLength() * matchedTrip.getPPK())
                .sum();
    }

    private void calculatePersonalFuelConsumption() {
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());
        personalFuelConsumption = subRoutePath
                .stream()
                .mapToDouble(TransPoolPath::getFuelConsumption)
                .sum();
    }

    private void calculateExpectedTimeOfArrival() {
        expectedTimeOfArrival = transpoolTripRequest.getTimeOfDeparture();
        List<TransPoolPath> subRoutePath = matchedTrip
                .getRoute()
                .getSubRouteAsPathList(transpoolTripRequest.getSource(),transpoolTripRequest.getDestination());
        int minutesToAdd = subRoutePath
                .stream()
                .mapToInt(TransPoolPath::getPathTime)
                .sum();
        expectedTimeOfArrival.addMinutes(minutesToAdd);
    }

    public TransPoolTripRequest getTranspoolTripRequest() {
        return transpoolTripRequest;
    }

    public TransPoolTrip getMatchedTrip() {
        return matchedTrip;
    }

    public Time getExpectedTimeOfArrival() {
        return expectedTimeOfArrival;
    }

    public double getPersonalFuelConsumption() {
        return personalFuelConsumption;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    @Override
    public int getID() {
        return transpoolTripRequest.getID();
    }

    @Override
    public String getRequesterName() {
        return transpoolTripRequest.getRequesterName();
    }

    @Override
    public String getSource() {
        return transpoolTripRequest.getSource();
    }

    @Override
    public String getDestination() {
        return transpoolTripRequest.getDestination();
    }

    @Override
    public Time getTimeOfDeparture() {
        return transpoolTripRequest.getTimeOfDeparture();
    }

    @Override
    public boolean isContinuous() {
        return transpoolTripRequest.isContinuous();
    }


}
