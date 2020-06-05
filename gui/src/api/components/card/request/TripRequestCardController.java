package api.components.card.request;
import api.Constants;
import api.components.card.CardController;
import api.components.form.Form;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.function.Consumer;

public class TripRequestCardController extends CardController<TransPoolTripRequest> {

    @FXML private Label labelRiderName;
    @FXML private Label labelRequestID;
    @FXML private Label labelRequestSource;
    @FXML private Label labelRequestDestination;
    @FXML private Label labelArrivalDeparture;
    @FXML private Label labelTime;
    @FXML private AnchorPane anchorPaneTripRequestCardBody;

    @Override
    protected void updateItem(TransPoolTripRequest request, boolean empty) {
        super.updateItem(request, empty);
        if (empty || request == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(Constants.TRIP_REQUEST_CARD_RESOURCE);
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            initializeValues(request);

            setText(null);
            setGraphic(anchorPaneTripRequestCardBody);
        }
    }

    @Override
    public void initializeValues(TransPoolTripRequest request) {
        labelRequestID.textProperty().bind(request.requestIDProperty().asString());
        labelRiderName.textProperty().bind(request.getTranspoolRider().usernameProperty());
        labelRequestSource.textProperty().bind(Bindings.concat("Gets on at ", request.sourceStopProperty()));
        labelRequestDestination.textProperty().bind(Bindings.concat("Gets off at ", request.destinationStopProperty()));
        labelTime.textProperty().bind(request.requestTimeProperty().asString());
        labelArrivalDeparture.textProperty().bind(
                Bindings
                        .when(request.isTimeOfArrivalProperty())
                        .then("Requested time of arrival ")
                        .otherwise("Requested time of departure ")
        );
    }
}

