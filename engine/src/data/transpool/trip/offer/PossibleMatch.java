package data.transpool.trip.offer;

import data.transpool.trip.Route;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;

public class PossibleMatch extends BasicTripOfferData {

    private ObjectProperty<LocalTime> estimatedTimeOfArrival;

    public PossibleMatch(String sourceStop, String destinationStop, TripOffer possibleTripOffer) {
        super(possibleTripOffer);
        this.estimatedTimeOfArrival = new SimpleObjectProperty<>();
        this.route = new SimpleObjectProperty<>(possibleTripOffer.getRoute().subRoute(sourceStop, destinationStop));
        initialize();
    }


    @Override
    public void initialize() {
        this.tripPrice.set(getRoute().calculatePriceOfRoute(getPPK()));
        this.tripDurationInMinutes.set(getRoute().calculateTripDuration());
        this.averageFuelConsumption.set(getRoute().calculateAverageFuelConsumption());
        this.estimatedTimeOfArrival.set(getScheduling()
                .getDepartureTime()
                .plusMinutes(getTripDurationInMinutes()));
    }

    public LocalTime getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival.get();
    }

    @Override
    public String toString() {
        return "+-----------------TransPool Trip-----------------+\n"
                + "TransPool Trip ID:                       " + getOfferID() + "\n"
                + "Driver name:                             " + getTransPoolDriver() + "\n"
                + "Estimated time of arrival:               " + getEstimatedTimeOfArrival() + "\n"
                + "Average fuel consumption:                " + getAverageFuelConsumption() + "\n"
                + "Trip price:                              " + getPrice() + "\n";

    }
}
