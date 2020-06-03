package api.components.trips.bar.match;

import api.components.trips.bar.Clearable;
import api.components.TransPoolController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import data.transpool.TransPoolData;
import data.transpool.trip.PossibleMatch;
import data.transpool.trip.TransPoolTripRequest;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MatchTripController implements Clearable {

    private TransPoolController transPoolController;

    @FXML private JFXComboBox<TransPoolTripRequest> comboBoxRideID;
    @FXML private JFXTextField textFieldNumOfResultsToFind;
    @FXML private JFXListView<PossibleMatch> listViewResults;
    @FXML private JFXButton buttonMatchTrip;
    @FXML private JFXButton buttonSearchTrips;
    @FXML private JFXButton buttonClearTrips;

    private BooleanProperty fileLoaded;
    private BooleanProperty foundResults;


    public MatchTripController() {
        fileLoaded = new SimpleBooleanProperty();
        foundResults = new SimpleBooleanProperty(false);
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize(){
        buttonMatchTrip.disableProperty().bind(foundResults.not());
        buttonSearchTrips.disableProperty().bind(foundResults);
        buttonClearTrips.disableProperty().bind(buttonSearchTrips.disableProperty().not());
    }

    @FXML
    public void searchTripsButtonAction(ActionEvent event) {
        transPoolController.searchForMatches();
    }

    @FXML void clearTripsButtonAction(ActionEvent event) {
        transPoolController.clearForm(this);
    }

    @FXML
    void createNewMatchButtonAction(ActionEvent event) {
        transPoolController.createNewMatch();
    }

    @Override
    public void clear() {
        transPoolController.getEngine().clearPossibleMatches();
        textFieldNumOfResultsToFind.clear();

    }

    public void searchForMatches() {
        TransPoolTripRequest requestToMatch = comboBoxRideID.getValue();
        int maximumMatches = Integer.parseInt(textFieldNumOfResultsToFind.getText());
        transPoolController.getEngine().findPossibleMatches(requestToMatch, maximumMatches);
    }

    public void createNewMatch() {
        transPoolController.getEngine().addNewMatch(listViewResults.getSelectionModel().getSelectedItem());
        transPoolController.clearForm(this);
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }

    public void bindUIToData(TransPoolData data) {
        foundResults.bind(transPoolController.getEngine().foundMatchesProperty());
        comboBoxRideID.setItems(data.getAllTransPoolTripRequests());
        listViewResults.setItems(transPoolController.getEngine().getPossibleMatches());
    }
}
