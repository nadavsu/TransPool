package data.transpool.trip.offer.matching;

import data.transpool.time.TimeDay;
import data.transpool.trip.offer.graph.SubTripOffer;
import data.transpool.user.TransPoolDriver;

import java.util.ArrayList;
import java.util.List;

public class PossibleRoute {

    private List<SubTripOffer> route;
    private int length;

    private int totalPrice;
    private double totalFuelConsumption;
    private int totalTripDuration;
    private boolean isContinuous;

    private double averageFuelConsumption;
    private TimeDay timeOfArrival;
    private TimeDay timeOfDeparture;

    public PossibleRoute() {
        this.route = new ArrayList<>();
        this.length = 0;
        this.totalPrice = 0;
        this.totalTripDuration = 0;
        this.totalFuelConsumption = 0;
        this.averageFuelConsumption = 0;
        this.isContinuous = false;
    }

    public PossibleRoute(PossibleRoute other) {
        this.route = new ArrayList<>(other.route);
        this.length = other.length;
        this.totalPrice = other.totalPrice;
        this.totalTripDuration = other.totalTripDuration;
        this.totalFuelConsumption = other.totalFuelConsumption;
        this.averageFuelConsumption = other.averageFuelConsumption;
        this.isContinuous = other.isContinuous;
        this.timeOfArrival = new TimeDay(other.timeOfArrival);
        this.timeOfDeparture = new TimeDay(other.timeOfDeparture);
    }

    public boolean add(SubTripOffer offer, TimeDay departureTime) {
        if (offer.getOccurrenceAfter(departureTime)) {
            if (length == 0) {
                return this.addToEmpty(offer);
            } else {
                return this.addToNotEmpty(offer);
            }
        } else {
            return false;
        }
    }

    private boolean addToEmpty(SubTripOffer offer) {
        this.route.add(offer);
        this.length++;

        this.isContinuous = true;
        this.timeOfDeparture = new TimeDay(offer.getTimeOfDepartureFromSource());
        this.totalPrice += offer.getPrice();
        this.totalFuelConsumption += offer.getAverageFuelConsumption();
        this.totalTripDuration += offer.getTripDurationInMinutes();
        this.averageFuelConsumption = totalFuelConsumption / length;
        this.timeOfArrival = new TimeDay(offer.getTimeOfArrivalAtDestination());
        return true;
    }

    private boolean addToNotEmpty(SubTripOffer offer) {
        this.route.add(offer);
        this.length++;

        this.totalPrice += offer.getPrice();
        this.totalFuelConsumption += offer.getAverageFuelConsumption();
        this.totalTripDuration += offer.getTripDurationInMinutes();
        this.averageFuelConsumption = totalFuelConsumption / length;
        this.timeOfArrival = new TimeDay(offer.getTimeOfArrivalAtDestination());
        this.isContinuous = isContinuous && route.get(length - 2).getTransPoolDriver()
                .equals(route.get(length - 1).getTransPoolDriver());
        return true;
    }

    public void remove(SubTripOffer offer) {
        this.route.remove(length - 1);
        this.length--;
        this.totalPrice -= offer.getPrice();
        this.totalFuelConsumption -= offer.getAverageFuelConsumption();
        this.totalTripDuration -= offer.getTripDurationInMinutes();
        if (!isContinuous) {
            checkContinuous();
        }
        if(length > 0) {
            this.averageFuelConsumption = totalFuelConsumption / length;
            this.timeOfArrival = route.get(length - 1).getTimeOfArrivalAtDestination();
        } else {
            this.averageFuelConsumption = 0;
            this.timeOfArrival = null;
            this.timeOfDeparture = null;
        }
    }

    public SubTripOffer lastOffer() {
        return this.route.get(length - 1);
    }

    private void checkContinuous() {
        isContinuous = true;
        TransPoolDriver startingDriver = route.get(0).getTransPoolDriver();
        for (SubTripOffer offer : route) {
            isContinuous = isContinuous
                    && offer.getTransPoolDriver()
                    .equals(startingDriver);
        }
    }

    public List<SubTripOffer> getRoute() {
        return route;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public int getLength() {
        return length;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public int getTotalTripDuration() {
        return totalTripDuration;
    }

    public TimeDay getTimeOfArrival() {
        return timeOfArrival;
    }

    public double getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public TimeDay getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public int getDayStart() {
        return timeOfDeparture.getDay();
    }

    public int getDayEnd() {
        return timeOfArrival.getDay();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (SubTripOffer offer : route) {
            str.append(offer.toString());
            str.append("\n");
        }
        str.append("\nTRIP SUMMARY:\n");
        str.append("Total price: ").append(totalPrice).append("\n");
        str.append("Average fuel consumption: ").append(averageFuelConsumption).append("\n");
        str.append("Time of arrival: ").append(timeOfArrival);
        return str.toString();
    }
}
