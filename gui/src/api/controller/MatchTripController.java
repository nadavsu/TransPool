package api.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MatchTripController extends Controller{

    @FXML private JFXComboBox<?> comboBoxRideID;
    @FXML private JFXTextField comboBoxNumOfResults;
    @FXML private JFXListView<?> listViewResults;
    @FXML private JFXButton buttonMatchTrip;

    @FXML
    void createNewMatch(ActionEvent event) {

    }

}
