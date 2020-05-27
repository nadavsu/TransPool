package api.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class TransPoolTripOfferController extends Controller {

    @FXML private JFXTextField textFieldDriverName;
    @FXML private JFXTimePicker timeFieldDepatureTime;
    @FXML private JFXTextField textFieldDay;
    @FXML private JFXComboBox<?> textFieldRecurrences;
    @FXML private JFXComboBox<?> comboBoxRiderCapacity;
    @FXML private JFXTextField textFieldStopToAdd;
    @FXML private JFXListView<?> listViewRoute;
    @FXML private JFXTextField textFieldPPK;
    @FXML private JFXButton buttonAddTransPoolTrip;

    @FXML
    void addNewTransPoolTrip(ActionEvent event) {

    }

}

