package data.transpool.trip.request;

import data.transpool.time.TimeDay;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.user.TransPoolDriver;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class MatchedTripRequest extends BasicTripRequestData {

    private ObservableList<Integer> tripOfferIDs;
    private ObservableList<TransPoolDriver> transpoolDrivers;
    private IntegerProperty tripPrice;
    private DoubleProperty personalFuelConsumption;
    private ObjectProperty<TimeDay> expectedTimeOfArrival;

    public MatchedTripRequest(TripRequest tripRequestToMatch, PossibleRoute possibleRoute) {
        super(tripRequestToMatch);
        this.tripPrice = new SimpleIntegerProperty(possibleRoute.getTotalPrice());
        this.expectedTimeOfArrival = new SimpleObjectProperty<>(possibleRoute.getTimeOfArrival());
        this.personalFuelConsumption = new SimpleDoubleProperty(possibleRoute.getAverageFuelConsumption());
        this.tripOfferIDs = FXCollections.observableArrayList();
        this.transpoolDrivers = FXCollections.observableArrayList();

        possibleRoute.getRoute().forEach(subTripOffer -> {
            tripOfferIDs.add(subTripOffer.getOfferID());
            transpoolDrivers.add(subTripOffer.getTransPoolDriver());
        });
    }

    public ObservableList<Integer> getTripOfferIDs() {
        return tripOfferIDs;
    }


    public ObservableList<TransPoolDriver> getTransPoolDrivers() {
        return transpoolDrivers;
    }


    public int getTripPrice() {
        return tripPrice.get();
    }

    public IntegerProperty tripPriceProperty() {
        return tripPrice;
    }

    public void setTripPrice(int tripPrice) {
        this.tripPrice.set(tripPrice);
    }

    public double getPersonalFuelConsumption() {
        return personalFuelConsumption.get();
    }

    public DoubleProperty personalFuelConsumptionProperty() {
        return personalFuelConsumption;
    }

    public void setPersonalFuelConsumption(double personalFuelConsumption) {
        this.personalFuelConsumption.set(personalFuelConsumption);
    }

    public TimeDay getExpectedTimeOfArrival() {
        return expectedTimeOfArrival.get();
    }

    public ObjectProperty<TimeDay> expectedTimeOfArrivalProperty() {
        return expectedTimeOfArrival;
    }

    public void setExpectedTimeOfArrival(TimeDay expectedTimeOfArrival) {
        this.expectedTimeOfArrival.set(expectedTimeOfArrival);
    }

    @Override
    public String toString() {
        return super.toString();
        /*String matchedTripString = "";
        matchedTripString += "------Matched Trip------\n";
        matchedTripString += "Request ID: " + getRequestID() + "\n";
        matchedTripString += "Name of rider: " + getTransPoolRider() + "\n";
        matchedTripString += "Source stop: " + getSourceStop() + "\n";
        matchedTripString += "Destination stop: " + getDestinationStop() + "\n";
        matchedTripString += "Matched trip ID: " + getTripOfferID() + "\n";
        matchedTripString += "Name of driver: " + getTransPoolDriver() + "\n";
        matchedTripString += "Price of trip: " + getTripPrice() + "\n";
        matchedTripString += "Expected time of arrival: " + getExpectedTimeOfArrival() + "\n";
        matchedTripString += "Personal fuel consumption: " + getPersonalFuelConsumption() + "\n";

        return matchedTripString;*/
    }
}
