package data.transpool.trip.request;

import data.transpool.trip.offer.BasicTripOffer;
import data.transpool.trip.offer.PossibleMatch;
import data.transpool.user.TransPoolDriver;
import javafx.beans.property.*;

import java.time.LocalTime;

public class MatchedTripRequest extends BasicTripRequestData {

    private IntegerProperty tripOfferID;
    private ObjectProperty<TransPoolDriver> transpoolDriver;
    private IntegerProperty tripPrice;
    private DoubleProperty personalFuelConsumption;
    private ObjectProperty<LocalTime> expectedTimeOfArrival;

    public MatchedTripRequest(TripRequest tripRequestToMatch, PossibleMatch tripOfferToMatch) {
        super(tripRequestToMatch);
        this.tripPrice = new SimpleIntegerProperty(tripOfferToMatch.getPrice());
        this.expectedTimeOfArrival = new SimpleObjectProperty<>(tripOfferToMatch.getEstimatedTimeOfArrival());
        this.personalFuelConsumption = new SimpleDoubleProperty(tripOfferToMatch.getAverageFuelConsumption());

        this.tripOfferID = new SimpleIntegerProperty(tripOfferToMatch.getOfferID());
        this.transpoolDriver = tripOfferToMatch.transpoolDriverProperty();
    }

    public int getTripOfferID() {
        return tripOfferID.get();
    }

    public IntegerProperty tripOfferIDProperty() {
        return tripOfferID;
    }

    public void setTripOfferID(int tripOfferID) {
        this.tripOfferID.set(tripOfferID);
    }

    public TransPoolDriver getTransPoolDriver() {
        return transpoolDriver.get();
    }

    public ObjectProperty<TransPoolDriver> transpoolDriverProperty() {
        return transpoolDriver;
    }

    public void setTransPoolDriver(TransPoolDriver transpoolDriver) {
        this.transpoolDriver.set(transpoolDriver);
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

    public LocalTime getExpectedTimeOfArrival() {
        return expectedTimeOfArrival.get();
    }

    public ObjectProperty<LocalTime> expectedTimeOfArrivalProperty() {
        return expectedTimeOfArrival;
    }

    public void setExpectedTimeOfArrival(LocalTime expectedTimeOfArrival) {
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
