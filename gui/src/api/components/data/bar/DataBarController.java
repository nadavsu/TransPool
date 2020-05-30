package api.components.data.bar;

import api.components.main.TransPoolController;
import com.jfoenix.controls.JFXListView;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import javafx.fxml.FXML;

public class DataBarController {

    private TransPoolController transPoolController;

    @FXML private JFXListView<TransPoolTripRequest> listViewTripRequests;
    @FXML private JFXListView<TransPoolTrip> listViewTripOffers;


    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize() {
    }

    public void updateData() {
        listViewTripRequests.setItems(transPoolController.getEngine().getAllTransPoolTripRequests());
        listViewTripOffers.setItems(transPoolController.getEngine().getAllTransPoolTrips());
    }
}

