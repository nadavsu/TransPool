package api.transpool.trip.matching.component;

import api.transpool.time.component.TimeDay;
import api.transpool.trip.offer.component.TripOfferPart;
import api.transpool.trip.offer.component.TripOfferPartOccurrence;
import api.transpool.user.account.TransPoolDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * An array list of TimeSubTripOffers and the summary details such as total price, fuel consumption etc.
 */
public class PossibleRoute {

    private List<TripOfferPartOccurrence> route;
    private int length;

    private int totalPrice;
    private double totalFuelConsumption;
    private int totalTripDuration;
    private boolean isContinuous;

    private double averageFuelConsumption;
    private TimeDay arrivalTime;
    private TimeDay departureTime;

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
        this.arrivalTime = new TimeDay(other.arrivalTime);
        this.departureTime = new TimeDay(other.departureTime);
    }

    public boolean add(TripOfferPart offer, TimeDay departureTime) {
        TripOfferPartOccurrence offerOccurrence = offer.getOccurrenceAfter(departureTime);
        if (offerOccurrence != null) {
            if (length == 0) {
                return this.addToEmpty(offerOccurrence);
            } else {
                return this.addToNotEmpty(offerOccurrence);
            }
        } else {
            return false;
        }
    }

    private boolean addToEmpty(TripOfferPartOccurrence offerOccurrence) {
        this.route.add(offerOccurrence);
        this.length++;

        this.isContinuous = true;
        this.departureTime = new TimeDay(offerOccurrence.getOccurrenceStart());
        this.totalPrice += offerOccurrence.getPrice();
        this.totalFuelConsumption += offerOccurrence.getAverageFuelConsumption();
        this.totalTripDuration += offerOccurrence.getTripDurationInMinutes();
        this.averageFuelConsumption = totalFuelConsumption / length;
        this.arrivalTime = new TimeDay(offerOccurrence.getArrivalTime());
        return true;
    }

    private boolean addToNotEmpty(TripOfferPartOccurrence offerOcurrence) {
        this.route.add(offerOcurrence);
        this.length++;

        this.totalPrice += offerOcurrence.getPrice();
        this.totalFuelConsumption += offerOcurrence.getAverageFuelConsumption();
        this.totalTripDuration += offerOcurrence.getTripDurationInMinutes();
        this.averageFuelConsumption = totalFuelConsumption / length;
        this.arrivalTime = new TimeDay(offerOcurrence.getArrivalTime());

        //Checking if the ride is continuous throughout the ride.
        this.isContinuous = isContinuous && route.get(length - 2).getTransPoolDriver()
                .equals(route.get(length - 1).getTransPoolDriver());
        return true;
    }

    public void remove(TripOfferPart offer) {
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
            this.arrivalTime = route.get(length - 1).getArrivalTime();
        } else {
            this.averageFuelConsumption = 0;
            this.arrivalTime = null;
            this.departureTime = null;
        }
    }

    public TripOfferPartOccurrence lastOffer() {
        return this.route.get(length - 1);
    }


    /**
     * Bonus #4 - The algorithm checks if the ride is continuous by checking if the TransPool rider is the same throughout
     * the whole ride.
     */
    private void checkContinuous() {
        isContinuous = true;
        TransPoolDriver startingDriver = route.get(0).getTransPoolDriver();
        for (TripOfferPartOccurrence offer : route) {
            isContinuous = isContinuous && offer.getTransPoolDriver().equals(startingDriver);
        }
    }

    public List<TripOfferPartOccurrence> getRoute() {
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

    public TimeDay getArrivalTime() {
        return arrivalTime;
    }

    public double getTotalFuelConsumption() {
        return totalFuelConsumption;
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public int getDayStart() {
        return departureTime.getDay();
    }

    public int getDayEnd() {
        return arrivalTime.getDay();
    }

    public PossibleRouteDTO getDetails() {
        return new PossibleRouteDTO(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (TripOfferPartOccurrence offer : route) {
            str.append(offer.toString());
            str.append("\n");
        }
        str.append("\nTRIP SUMMARY:\n");
        str.append("Total price: ").append(totalPrice).append("\n");
        str.append("Average fuel consumption: ").append(averageFuelConsumption).append("\n");
        str.append("Time of arrival: ").append(arrivalTime);
        return str.toString();
    }
}
