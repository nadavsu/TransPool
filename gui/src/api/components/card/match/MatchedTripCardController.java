package api.components.card.match;

import api.Constants;
import api.components.card.CardController;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MatchedTripCardController extends CardController<MatchedTripRequest> {


    @FXML private Label labelRiderName;
    @FXML private Label labelRequestID;
    @FXML private Label labelRequestSource;
    @FXML private Label labelRequestDestination;
    @FXML private Label labelTripOfferID;
    @FXML private Label labelDriverName;
    @FXML private Label labelTripPrice;
    @FXML private Label labelPersonalFuelConsumption;
    @FXML private Label labelExpectedTimeOfArrival;
    @FXML private AnchorPane anchorPaneCardBody;

    @Override
    protected void updateItem(MatchedTripRequest request, boolean empty) {
        super.updateItem(request, empty);
        if (empty || request == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(Constants.MATCHED_TRIP_CARD_RESOURCE);
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            initializeValues(request);

            setText(null);
            setGraphic(anchorPaneCardBody);
        }
    }

    @Override
    protected void initializeValues(MatchedTripRequest request) {
        labelRequestID.textProperty().bind(request.requestIDProperty().asString());
        labelRiderName.textProperty().bind(request.getTransPoolRider().usernameProperty());
        labelRequestSource.textProperty().bind(Bindings.concat("Gets on at ", request.sourceStopProperty()));
        labelRequestDestination.textProperty().bind(Bindings.concat("Gets off at ", request.destinationStopProperty()));
        labelTripOfferID.textProperty().bind(request.tripOfferIDProperty().asString());
        labelDriverName.textProperty().bind(Bindings.concat(
                "Riding with ", request.getTransPoolDriver().getUsername()));
        labelTripPrice.textProperty().bind(Bindings.concat(
                "Trip price: ", request.getTripPrice()));
        labelPersonalFuelConsumption.textProperty().bind(Bindings.concat(
                "By travelling by TransPool, you have saved ", request.getPersonalFuelConsumption()
                , " litres of fuel!"));
        labelExpectedTimeOfArrival.textProperty().bind(Bindings.concat(
                "Expected to arrive by ", request.getExpectedTimeOfArrival()));
    }
}