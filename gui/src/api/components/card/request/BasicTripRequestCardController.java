package api.components.card.request;

import api.components.card.CardController;
import data.transpool.trip.request.BasicTripRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class BasicTripRequestCardController<T extends BasicTripRequest> extends CardController<BasicTripRequest> {

    @FXML protected Label labelRiderName;
    @FXML protected Label labelRequestID;
    @FXML protected Label labelRequestSource;
    @FXML protected Label labelRequestDestination;
    @FXML protected AnchorPane anchorPaneCardBody;

    public void loadController(){

    }

    @Override
    protected void initializeValues(BasicTripRequest basicTripRequest) {

    }

    @Override
    protected void updateItem(BasicTripRequest request, boolean empty) {
        super.updateItem(request, empty);
        if (empty || request == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loadController();
            }
            initializeValues(request);

            setText(null);
            setGraphic(anchorPaneCardBody);
        }
    }

}
