package api.components.cards.request;
import api.Constants;
import data.transpool.trip.TransPoolTripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class TripRequestCardController extends ListCell<TransPoolTripRequest> {

    @FXML private Label labelRiderName;
    @FXML private Label labelRequestID;
    @FXML private Label labelRequestSource;
    @FXML private Label labelRequestDestination;
    @FXML private Label labelArrivalDeparture;
    @FXML private Label labelTime;
    @FXML private AnchorPane anchorPaneTripRequestCardBody;

    private FXMLLoader loader;

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

            setText(null);
            setGraphic(anchorPaneTripRequestCardBody);
        }
    }
}

