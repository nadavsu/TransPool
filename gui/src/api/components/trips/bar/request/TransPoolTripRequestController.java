package api.components.trips.bar.request;

import api.components.TransPoolController;
import api.components.UICardAdapter;
import api.components.cards.request.TripRequestCardController;
import api.components.trips.bar.Clearable;
import com.jfoenix.controls.*;
import data.transpool.trip.TransPoolTripRequest;
import exception.file.StopNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TransPoolTripRequestController implements Clearable {

    private TransPoolController transPoolController;

    @FXML private JFXTimePicker timeFieldTime;
    @FXML private JFXRadioButton radioButtonArrivalTime;
    @FXML private JFXRadioButton radioButtonDepartureTime;
    @FXML private JFXTextField textFieldSource;
    @FXML private JFXTextField textFieldRiderName;
    @FXML private JFXTextField textFieldDestination;
    @FXML private JFXButton buttonAddRequest;

    private BooleanProperty fileLoaded;
    private Map<Integer, TripRequestCardController> IDToTripRequest;

    public TransPoolTripRequestController() {
        fileLoaded = new SimpleBooleanProperty();
        IDToTripRequest = new HashMap<>();
    }

    @FXML
    public void initialize() {
        timeFieldTime.set24HourView(true);
        timeFieldTime.setValue(LocalTime.now());
        buttonAddRequest.disableProperty().bind(fileLoaded.not());
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void newTripRequestButtonAction(ActionEvent event) {
        transPoolController.addNewTripRequest();
    }

    /**
     * Rounds time to the nearest 5 minutes.
     */
    @FXML
    public void timeSelectedAction(ActionEvent event) {
        LocalTime accurateTime = timeFieldTime.getValue();
        LocalTime roundedTime = accurateTime.withSecond(0).withNano(0).plusMinutes((65-accurateTime.getMinute()) % 5);
        timeFieldTime.setValue(roundedTime);
    }

    public void addNewTripRequest() {
        String source = textFieldSource.getText();
        String destination = textFieldDestination.getText();
        String riderName = textFieldRiderName.getText();
        LocalTime time = timeFieldTime.getValue();
        boolean isArrivalTime = radioButtonArrivalTime.isSelected();

        try {
            transPoolController.getEngine().createNewTransPoolTripRequest(riderName, source, destination, time, isArrivalTime, true);
            transPoolController.clearForm(this);
        } catch (StopNotFoundException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input error.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @Override
    public void clear() {
        timeFieldTime.setValue(LocalTime.now());
        radioButtonDepartureTime.setSelected(true);
        textFieldDestination.setText("");
        textFieldSource.setText("");
        textFieldRiderName.setText("");
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }

    public UICardAdapter<TransPoolTripRequest> createCardUIAdapter() {
        return new UICardAdapter<TransPoolTripRequest>(
                this::createCard,
                this::updateCard
        );
    }

    private void createCard(TransPoolTripRequest transPoolTripRequest) {
/*        try {
            //Loading the card using FXMLLoader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Constants.REQUEST_CARD_RESOURCE);
            Node tripRequestCard = loader.load();

            //Creating the controller
            TripRequestCardController tripRequestCardController = loader.getController();

            //Setting the values of the controller
            tripRequestCardController.setRequest(transPoolTripRequest);

            //Adding to UI.
            transPoolController.addTripRequestCard(tripRequestCard);

            //Updating the hashmap.
            IDToTripRequest.put(tripRequestCardController.getRequestID(), tripRequestCardController);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void updateCard(TransPoolTripRequest tripRequestData) {
/*        TripRequestCardController tripRequestCardController = IDToTripRequest.get(tripRequestData.getRequestID());
        if (tripRequestCardController != null) {
            tripRequestCardController.setRiderName(tripRequestData.getRiderName());
            tripRequestCardController.setSourceStop(tripRequestData.getSourceStop());
            tripRequestCardController.setDestinationStop(tripRequestData.getDestinationStop());
            tripRequestCardController.setRequestTime(tripRequestData.getRequestTime());
            tripRequestCardController.setTimeOfArrival(tripRequestData.isTimeOfArrival());
            tripRequestCardController.setContinuous(tripRequestData.isContinuous());
        }*/
    }
}
