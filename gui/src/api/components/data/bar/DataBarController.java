package api.components.data.bar;

import api.components.TransPoolController;
import api.components.card.offer.TripOfferCardController;
import api.components.card.request.TripRequestCardController;
import data.transpool.TransPoolData;
import data.transpool.trip.TransPoolTrip;
import data.transpool.trip.TransPoolTripRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DataBarController {

    private TransPoolController transPoolController;

    @FXML private Label labelTaskProgress;
    @FXML private ListView<TransPoolTripRequest> listViewTripRequests;
    @FXML private ListView<TransPoolTrip> listViewTripOffers;

    private StringProperty currentTaskProgress;
    private ObservableList<TransPoolTripRequest> tripRequests;


    public DataBarController() {
        currentTaskProgress = new SimpleStringProperty();
        tripRequests = FXCollections.observableArrayList();
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize() {
        labelTaskProgress.textProperty().bind(currentTaskProgress);
    }

    public StringProperty currentTaskProgressProperty() {
        return currentTaskProgress;
    }

    public void bindTaskToUI(Task theTask) {
        if (currentTaskProgress.isBound()) {
            currentTaskProgress.unbind();
        }
        currentTaskProgress.bind(theTask.messageProperty());
    }

    public void bindUIToData(TransPoolData data) {
        listViewTripOffers.setItems(data.getAllTransPoolTrips());
        listViewTripRequests.setItems(data.getAllTransPoolTripRequests());
        listViewTripRequests.setCellFactory(requestListView -> new TripRequestCardController());
        listViewTripOffers.setItems(transPoolController.getEngine().getAllTransPoolTrips());
        listViewTripOffers.setCellFactory(offerListView -> new TripOfferCardController());
    }

}

