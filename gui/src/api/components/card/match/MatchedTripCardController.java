package api.components.card.match;

import api.Constants;
import api.components.card.CardController;
import api.components.card.request.BasicTripRequestCardController;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class MatchedTripCardController extends BasicTripRequestCardController<MatchedTripRequest> {


    @FXML private Label labelTripOfferID;
    @FXML private Label labelDriverName;
    @FXML private Label labelTripPrice;
    @FXML private Label labelPersonalFuelConsumption;
    @FXML private Label labelExpectedTimeOfArrival;

    @Override
    public void loadController() {
        loader = new FXMLLoader(Constants.MATCHED_TRIP_CARD_RESOURCE);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeValues(BasicTripRequest request) {
        labelRequestID.textProperty().bind(request.requestIDProperty().asString());
        labelRiderName.textProperty().bind(request.getTransPoolRider().usernameProperty());
        labelRequestSource.textProperty().bind(Bindings.concat("Gets on at ", request.sourceStopProperty()));
        labelRequestDestination.textProperty().bind(Bindings.concat("Gets off at ", request.destinationStopProperty()));
        labelTripOfferID.textProperty().bind(((MatchedTripRequest) request).tripOfferIDProperty().asString());
        labelDriverName.textProperty().bind(Bindings.concat(
                "Riding with ", ((MatchedTripRequest) request).getTransPoolDriver().getUsername()));
        labelTripPrice.textProperty().bind(Bindings.concat(
                "Trip price: ", ((MatchedTripRequest) request).getTripPrice()));
        labelPersonalFuelConsumption.textProperty().bind(Bindings.concat(
                "By travelling TransPool, you have saved ", ((MatchedTripRequest) request).getPersonalFuelConsumption()
                , "litres of fuel!"));
        labelExpectedTimeOfArrival.textProperty().bind(Bindings.concat(
                "Expected to arrive by ", ((MatchedTripRequest) request).getExpectedTimeOfArrival()));
    }
}