package api.components.cards.offer;

import api.Constants;
import com.jfoenix.controls.JFXListView;
import data.transpool.trip.TransPoolTrip;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TripOfferCardController extends ListCell<TransPoolTrip> {
    @FXML private Label labelDriverName;
    @FXML private Label labelDriverRating;
    @FXML private Label labelOfferID;
    @FXML private Label labelSchedule;
    @FXML private JFXListView<String> listViewStops;
    @FXML private Label labelTripDuration;
    @FXML private Label labelFuelConsumption;
    @FXML private Label labelPPK;
    @FXML private JFXListView<TransPoolTrip.RiderStatus> listViewRiderDetails;
    @FXML private Label labelPassengerCapacity;
    @FXML private AnchorPane anchorPaneTripOfferCardBody;

    private FXMLLoader loader;

    protected void updateItem(TransPoolTrip tripOffer, boolean empty) {
        super.updateItem(tripOffer, empty);

        if (empty || tripOffer == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(Constants.TRIP_OFFER_CARD_RESOURCE);
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            labelDriverName.textProperty().bind(tripOffer.transpoolDriverProperty().get().usernameProperty());
            labelOfferID.textProperty().bind(tripOffer.offerIDProperty().asString());

            listViewStops.setItems(tripOffer.getRoute().getRoute());
            listViewRiderDetails.setItems(tripOffer.getAllRiderStatuses());

            labelPassengerCapacity.textProperty().bind(Bindings.concat(
                    "There are ", tripOffer.passengerCapacityProperty(), " spaces left on this ride."));
            labelTripDuration.textProperty().bind(Bindings.concat(
                    "Trip is about ", tripOffer.tripDurationInMinutesProperty(), " minutes long."));
            labelFuelConsumption.textProperty().bind(Bindings.concat(
                    "Average fuel consumption is about ", tripOffer.averageFuelConsumptionProperty()));
            labelPPK.textProperty().bind(Bindings.concat(
                    "Price per kilometer: ", tripOffer.PPKProperty()));
            labelSchedule.textProperty().bind(Bindings.concat(
                    "Departs ", tripOffer.getSchedule().getRecurrences().toString(),
                    " on day ", tripOffer.getSchedule().getDay(),
                    " at ", tripOffer.getSchedule().getTime().toString()));
            labelDriverRating.textProperty().bind(Bindings
                    .when(tripOffer.ratingProperty().isEqualTo(0))
                    .then("No rating yet.")
                    .otherwise(tripOffer.ratingProperty().asString()));


            setText(null);
            setGraphic(anchorPaneTripOfferCardBody);
        }
    }

}
