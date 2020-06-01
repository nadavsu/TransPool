package api.components.trips.bar.match;

import api.components.trips.bar.Clearable;
import api.components.TransPoolController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MatchTripController implements Clearable {

    private TransPoolController transPoolController;

    @FXML private JFXComboBox<?> comboBoxRideID;
    @FXML private JFXTextField comboBoxNumOfResults;
    @FXML private JFXListView<?> listViewResults;
    @FXML private JFXButton buttonMatchTrip;

    private BooleanProperty fileLoaded;

    public MatchTripController() {
        fileLoaded = new SimpleBooleanProperty();
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize(){
        buttonMatchTrip.disableProperty().bind(fileLoaded.not());
    }

    @FXML
    void createNewMatch(ActionEvent event) {

    }

    @Override
    public void clear() {
        comboBoxNumOfResults.setText("0");
        listViewResults.getItems().clear();
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }
}
