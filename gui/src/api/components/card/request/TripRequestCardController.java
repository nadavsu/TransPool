package api.components.card.request;
import api.Constants;
import api.components.card.CardController;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.BasicTripRequestData;
import data.transpool.trip.request.TripRequest;
import data.transpool.trip.request.TripRequestData;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TripRequestCardController extends BasicTripRequestCardController<TripRequest> {

    @FXML private Label labelArrivalDeparture;
    @FXML private Label labelTime;

    @Override
    public void loadController() {
        loader = new FXMLLoader(Constants.TRIP_REQUEST_CARD_RESOURCE);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeValues(BasicTripRequest request) {
        labelRequestID.textProperty().bind(request.requestIDProperty().asString());
        labelRiderName.textProperty().bind(request.getTransPoolRider().usernameProperty());
        labelRequestSource.textProperty().bind(Bindings.concat("Gets on at ", request.sourceStopProperty()));
        labelRequestDestination.textProperty().bind(Bindings.concat("Gets off at ", request.destinationStopProperty()));
        labelTime.textProperty().bind(((TripRequest) request).requestTimeProperty().asString());
        labelArrivalDeparture.textProperty().bind(
                Bindings
                        .when(((TripRequest) request).isTimeOfArrivalProperty())
                        .then("Requested time of arrival ")
                        .otherwise("Requested time of departure ")
        );
    }
}

