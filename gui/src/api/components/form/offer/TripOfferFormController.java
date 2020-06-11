package api.components.form.offer;

import api.components.TransPoolController;
import api.components.form.Form;
import api.components.form.FormController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import data.transpool.TransPoolData;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalTime;

public class TripOfferFormController extends FormController {

    @FXML private JFXTextField textFieldDriverName;
    @FXML private JFXTimePicker timeFieldDepatureTime;
    @FXML private JFXTextField textFieldDay;
    @FXML private JFXComboBox<String> comboBoxRecurrences;
    @FXML private JFXComboBox<Integer> comboBoxRiderCapacity;
    @FXML private JFXComboBox<String> comboBoxStopToAdd;
    @FXML private JFXListView<String> listViewRoute;
    @FXML private JFXTextField textFieldPPK;
    @FXML private JFXButton buttonAddStop;
    @FXML private JFXButton buttonRemoveStop;
    @FXML private JFXButton buttonAddTransPoolTrip;

    private BooleanProperty fileLoaded;
    private ObservableList<String> addedStops;
    private ObservableList<String> allStops;

    public TripOfferFormController() {
        fileLoaded = new SimpleBooleanProperty();
        addedStops = FXCollections.observableArrayList();
        allStops = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        super.initialize();
        buttonAddTransPoolTrip.disableProperty().bind(fileLoaded.not());
        listViewRoute.setItems(addedStops);
        timeFieldDepatureTime.set24HourView(true);
        timeFieldDepatureTime.setValue(LocalTime.MIDNIGHT);
        comboBoxRiderCapacity.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
        comboBoxRiderCapacity.setValue(1);
        comboBoxRecurrences.getItems().addAll("One time", "Daily", "Bi-daily", "Weekly", "Monthly");
        comboBoxRecurrences.setValue("One time");
        textFieldPPK.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textFieldPPK.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        textFieldDay.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textFieldDay.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));

    }

    @FXML
    public void addStopButtonAction(ActionEvent event) {
        String selectedStop = comboBoxStopToAdd.getValue();
        if (selectedStop != null) {
            addedStops.add(comboBoxStopToAdd.getValue());
        }
    }

    @FXML public void removeStopButtonAction(ActionEvent event) {
        String stopNameToRemove = listViewRoute.getSelectionModel().getSelectedItem();
        if (stopNameToRemove != null) {
            addedStops.remove(stopNameToRemove);
            allStops.add(stopNameToRemove);
        }
    }

    @FXML
    public void addNewTripOfferButtonAction(ActionEvent event) {
        transpoolController.submitForm(this);
    }

    /**
     * Rounds time to the nearest 5 minutes.
     */
    @FXML
    public void timeSelectedAction(ActionEvent event) {
        LocalTime accurateTime = timeFieldDepatureTime.getValue();
        if (accurateTime != null) {
            LocalTime roundedTime = accurateTime.withSecond(0).withNano(0).plusMinutes((65 - accurateTime.getMinute()) % 5);
            timeFieldDepatureTime.setValue(roundedTime);
        }
    }

    @Override
    public void submit() {
        int dayStart = 1;
        int riderCapacity = comboBoxRiderCapacity.getValue();
        String driverName = textFieldDriverName.getText();
        String recurrences = comboBoxRecurrences.getValue();
        LocalTime departureTime = timeFieldDepatureTime.getValue();
        int PPK = Integer.parseInt(textFieldPPK.getText());
        if (!textFieldDay.getText().equals("")) {
            dayStart = Integer.parseInt(textFieldDay.getText());
        }
        transpoolController.createNewTripOffer(driverName, departureTime, dayStart, recurrences,
                riderCapacity, PPK, addedStops);
    }

    @Override
    public void clear() {
        timeFieldDepatureTime.setValue(LocalTime.now());
        textFieldDay.clear();
        textFieldDriverName.setText("");
        textFieldPPK.setText("");
        listViewRoute.getItems().clear();
        comboBoxRiderCapacity.setValue(1);
        comboBoxRecurrences.setValue("One time");
        addedStops.clear();
    }

    @Override
    public void setValidations() {
        timeFieldDepatureTime.getValidators().add(requiredFieldValidator);
        textFieldDriverName.getValidators().add(requiredFieldValidator);
        textFieldPPK.getValidators().add(requiredFieldValidator);
        textFieldDay.getValidators().add(requiredFieldValidator);

        timeFieldDepatureTime.focusedProperty().addListener((
                (observable, oldValue, newValue) -> timeFieldDepatureTime.validate()));
        textFieldDriverName.focusedProperty().addListener(
                ((observable, oldValue, newValue) -> textFieldDriverName.validate()));
        textFieldPPK.focusedProperty().addListener(
                ((observable, oldValue, newValue) -> textFieldPPK.validate())
        );
        textFieldDay.focusedProperty().addListener(
                (observable, oldValue, newValue) -> textFieldDay.validate());
    }

    @Override
    public boolean isValid() {
        return textFieldPPK.validate()
                && textFieldDriverName.validate()
                && timeFieldDepatureTime.validate()
                && textFieldDay.validate();
    }

    public void bindDataToUI(TransPoolData data) {
        data
                .getMap()
                .getAllStops()
                .forEach((name, stop) -> allStops.add(name));
        comboBoxStopToAdd.setItems(allStops);
        FXCollections.sort(allStops, String::compareToIgnoreCase);
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }
}



