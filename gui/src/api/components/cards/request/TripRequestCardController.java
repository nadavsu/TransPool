package api.components.cards.request;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalTime;

public class TripRequestCardController extends BasicTripRequestData {

    @FXML private Label labelRiderName;
    @FXML private Label labelRequestID;
    @FXML private Label labelRequestSource;
    @FXML private Label labelRequestDestination;
    @FXML private Label labelArrivalDeparture;
    @FXML private Label labelTime;

    public TripRequestCardController() {
        super(-1, "", "", "", LocalTime.MIDNIGHT, false, false);
    }

    @FXML
    public void initialize() {
        labelRiderName.textProperty().bind(riderName);
        labelRequestID.textProperty().bind(requestID.asString());
        labelRequestSource.textProperty().bind(Bindings.concat("Gets on at ", sourceStop));
        labelRequestDestination.textProperty().bind(Bindings.concat("Gets off at ", destinationStop));
        labelTime.textProperty().bind(requestTime.asString());
        labelArrivalDeparture.textProperty().bind(
                Bindings
                        .when(isTimeOfArrival)
                        .then("Requested time of arrival ")
                        .otherwise("Requested time of departure ")
        );
    }
}

