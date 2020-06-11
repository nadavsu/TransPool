package data.transpool.trip.offer.matching;

import data.transpool.trip.offer.graph.SubTripOffer;
import data.transpool.user.TransPoolDriver;

import java.time.LocalTime;
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
    private LocalTime timeOfArrival;
    private LocalTime timeOfDeparture;
    private int dayStart;
    private int dayEnd;

    public PossibleRoute() {
        this.route = new ArrayList<>();
        this.length = 0;
        this.dayEnd = 0;
        this.dayStart = 0;
        this.totalPrice = 0;
        this.totalTripDuration = 0;
        this.totalFuelConsumption = 0;
        this.averageFuelConsumption = 0;
        this.isContinuous = false;
    }

    public PossibleRoute(PossibleRoute other) {
        this.route = new ArrayList<>(other.route);
        this.length = other.length;
        this.dayEnd = other.dayEnd;
        this.dayStart = other.dayStart;
        this.totalPrice = other.totalPrice;
        this.totalTripDuration = other.totalTripDuration;
        this.totalFuelConsumption = other.totalFuelConsumption;
        this.averageFuelConsumption = other.averageFuelConsumption;
        this.isContinuous = other.isContinuous;
        this.timeOfArrival = other.timeOfArrival;
        this.timeOfDeparture = other.timeOfDeparture;
    }

    public SubTripOffer lastOffer() {
        return this.route.get(length - 1);
    }

    //Only offers which are after the last offer, or if they're not one time (or both).
    public void add(SubTripOffer offer) {
        if (offer.isBefore(lastOffer())) {
            offer.setNextRecurrence();
        }

        this.route.add(offer);
        this.length++;

        if (length <= 1) {
            this.isContinuous = true;
            this.timeOfDeparture = offer.getTimeOfDepartureFromSource();
            this.dayStart = offer.getDay();
        } else {
            this.isContinuous = isContinuous
                    && route.get(length - 2).getTransPoolDriver()
                    .equals(route.get(length - 1).getTransPoolDriver());
        }
        this.totalPrice += offer.getPrice();
        this.totalFuelConsumption += offer.getAverageFuelConsumption();
        this.totalTripDuration += offer.getTripDurationInMinutes();
        this.averageFuelConsumption = totalFuelConsumption / length;
        this.timeOfArrival = offer.getTimeOfArrivalAtDestination();

/*        if (offer.getTimeOfArrivalAtDestination().isBefore(timeOfArrival)) {
            updateDayEnd();
        }*/

    }

    public void remove(SubTripOffer offer) {
        this.route.remove(offer);
        this.length--;
        this.totalPrice -= offer.getPrice();
        this.totalFuelConsumption -= offer.getAverageFuelConsumption();
        this.totalTripDuration -= offer.getTripDurationInMinutes();

        if (!isContinuous) {
            checkContinuous();
        }
        if(length > 0) {
            this.dayEnd = route.get(length - 1).getDay();
            this.averageFuelConsumption = totalFuelConsumption / length;
            this.timeOfArrival = route.get(length - 1).getTimeOfArrivalAtDestination();
        } else {
            this.averageFuelConsumption = 0;
            this.timeOfArrival = null;
            this.timeOfDeparture = null;
        }
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

    public LocalTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public double getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public LocalTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public int getDayStart() {
        return dayStart;
    }

    public int getDayEnd() {
        return dayEnd;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (SubTripOffer offer : route) {
            str.append(offer.toString());
            str.append("\n");
        }
        str.append("TRIP SUMMARY:\n");
        str.append("Total price: ").append(totalPrice).append("\n");
        str.append("Average fuel consumption: ").append(averageFuelConsumption).append("\n");
        str.append("Time of departure: ").append(timeOfDeparture).append("\n");
        str.append("Time of arrival: ").append(timeOfArrival);
        return str.toString();
    }
}
