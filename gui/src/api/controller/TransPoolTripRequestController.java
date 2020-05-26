package api.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TransPoolTripRequestController extends Controller {

    @FXML private JFXComboBox<?> comboBoxSource;
    @FXML private JFXTextField textFieldRiderName;
    @FXML private JFXComboBox<?> comboBoxDestination;
    @FXML private JFXButton buttonAddRequest;

    @FXML
    void addNewTripRequest(ActionEvent event) {

    }

}
