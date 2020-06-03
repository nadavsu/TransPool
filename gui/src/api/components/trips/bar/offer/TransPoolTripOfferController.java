package api.components.trips.bar.offer;

import api.components.TransPoolController;
import api.components.list.StopListCell;
import api.components.trips.bar.Clearable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import data.transpool.TransPoolData;
import data.transpool.map.Stop;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.swing.*;
import java.time.LocalTime;

public class TransPoolTripOfferController implements Clearable {

    private TransPoolController transpoolController;

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

    public TransPoolTripOfferController() {
        fileLoaded = new SimpleBooleanProperty();
        addedStops = FXCollections.observableArrayList();
        allStops = FXCollections.observableArrayList();
    }

    public void setTranspoolController(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
    }

    @FXML
    public void initialize() {
        timeFieldDepatureTime.set24HourView(true);
        comboBoxRiderCapacity.getItems().addAll(1, 2, 3, 4, 5);
        comboBoxRecurrences.getItems().addAll("One time", "Daily", "Bi-daily", "Weekly", "Monthly");
        buttonAddTransPoolTrip.disableProperty().bind(fileLoaded.not());
        listViewRoute.setItems(addedStops);
    }

    @FXML
    public void addStopButtonAction(ActionEvent event) {
        String selectedStop = comboBoxStopToAdd.getValue();
        if (selectedStop != null) {
            addedStops.add(comboBoxStopToAdd.getValue());
            allStops.remove(comboBoxStopToAdd.getValue());
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
        transpoolController.addNewTripOffer();
    }

    /**
     * Rounds time to the nearest 5 minutes.
     */
    @FXML
    public void timeSelectedAction(ActionEvent event) {
        LocalTime accurateTime = timeFieldDepatureTime.getValue();
        LocalTime roundedTime = accurateTime.withSecond(0).withNano(0).plusMinutes((65-accurateTime.getMinute()) % 5);
        timeFieldDepatureTime.setValue(roundedTime);
    }

    public void addNewTripOffer() {
        String driverName = textFieldDriverName.getText();
        String recurrences = comboBoxRecurrences.getValue();
        LocalTime departureTime = timeFieldDepatureTime.getValue();
        int dayStart = Integer.parseInt(textFieldDay.getText());
        int riderCapacity = comboBoxRiderCapacity.getValue();
        int PPK = Integer.parseInt(textFieldPPK.getText());

        //transpoolController.getEngine().createNewTripOffer(driverName, departureTime, dayStart, recurrences,
        //        riderCapacity, PPK, addedStops);

        transpoolController.clearForm(this);
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
        //reset allStops somehow.
    }

    public void bindDataToUI(TransPoolData data) {
        data
                .getMap()
                .getAllStops()
                .forEach((name, stop) -> allStops.add(name));
        comboBoxStopToAdd.setItems(allStops);
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }
}



