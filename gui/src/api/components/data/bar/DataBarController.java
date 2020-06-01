package api.components.data.bar;

import api.components.TransPoolController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class DataBarController {

    private TransPoolController transPoolController;

    @FXML private Label labelTaskProgress;
    @FXML private FlowPane flowPaneTripRequests;
    @FXML private FlowPane flowPaneTripOffers;

    private StringProperty currentTaskProgress;


    public DataBarController() {
        currentTaskProgress = new SimpleStringProperty();
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize() {
        labelTaskProgress.textProperty().bind(currentTaskProgress);
    }

    public void updateData() {

    }

    public StringProperty currentTaskProgressProperty() {
        return currentTaskProgress;
    }

    public void bindTaskToUI(Task theTask) {
        currentTaskProgress.bind(theTask.messageProperty());
    }

    public void addTripRequestCard(Node card) {
        flowPaneTripRequests.getChildren().add(card);
    }

    public void addTripOfferCard(Node card) {
        flowPaneTripOffers.getChildren().add(card);
    }
}

