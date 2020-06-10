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
        this.timeOfArrival = other.timeOfArrival;
        this.timeOfDeparture = other.timeOfDeparture;
    }

    public void add(SubTripOffer offer) {
        this.route.add(offer);
        this.length++;
        this.totalPrice += offer.getPrice();
        this.totalFuelConsumption += offer.getAverageFuelConsumption();
        this.totalTripDuration += offer.getTripDurationInMinutes();
        
        this.averageFuelConsumption = totalFuelConsumption / length;
        this.timeOfArrival = offer.getTimeOfArrivalAtDestination();

        if (length <= 1) {
            this.isContinuous = true;
            this.timeOfDeparture = offer.getTimeOfDepartureFromSource();
        } else {
            this.isContinuous = route.get(length - 2).getTransPoolDriver()
                    .equals(route.get(length - 1).getTransPoolDriver());
        }
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
            this.averageFuelConsumption = totalFuelConsumption / length;
            this.timeOfArrival = route.get(length - 1).getTimeOfArrivalAtDestination();
        } else {
            this.averageFuelConsumption = 0;
            this.timeOfArrival = null;
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
}
