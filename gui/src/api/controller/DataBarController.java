package api.controller;

import com.jfoenix.controls.JFXListView;
import data.transpool.trip.TransPoolTrip;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;

public class DataBarController {

    private TransPoolController transPoolController;

    @FXML private Accordion accordionAllTripRequests;
    @FXML private Accordion accordionAllTransPoolTrips;
    @FXML private JFXListView<String> listViewTest;


    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize() {
    }

    public void update() {
        listViewTest.setItems(transPoolController.getEngine().getAllTransPoolTripRequestsAsStrings());
    }
}

