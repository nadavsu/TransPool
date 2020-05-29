package api.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalTime;

public class TransPoolTripOfferController implements FormController {

    private TransPoolController transPoolController;

    @FXML private JFXTextField textFieldDriverName;
    @FXML private JFXTimePicker timeFieldDepatureTime;
    @FXML private JFXTextField textFieldDay;
    @FXML private JFXComboBox<String> comboBoxRecurrences;
    @FXML private JFXComboBox<Integer> comboBoxRiderCapacity;
    @FXML private JFXTextField textFieldStopToAdd;
    @FXML private JFXListView<?> listViewRoute;
    @FXML private JFXTextField textFieldPPK;
    @FXML private JFXButton buttonAddTransPoolTrip;

    private BooleanProperty fileLoaded;

    public TransPoolTripOfferController() {
        fileLoaded = new SimpleBooleanProperty(false);
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize() {
        timeFieldDepatureTime.set24HourView(true);
        comboBoxRiderCapacity.getItems().addAll(1, 2, 3, 4, 5);
        buttonAddTransPoolTrip.disableProperty().bind(fileLoaded.not());
    }

    @FXML
    void addNewTransPoolTrip(ActionEvent event) {

    }

    @Override
    public void clearForm() {
        timeFieldDepatureTime.setValue(LocalTime.now());
        textFieldDay.setText("0");
        textFieldDriverName.setText("");
        textFieldDriverName.setText("");
        textFieldPPK.setText("");
        textFieldStopToAdd.setText("");
        listViewRoute.getItems().clear();
        comboBoxRiderCapacity.setValue(1);
        comboBoxRecurrences.setValue("One time");
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }
}

