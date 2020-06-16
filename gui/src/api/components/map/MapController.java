package api.components.map;

import api.components.TransPoolController;
import api.components.map.course.transpool.graph.component.coordinate.CoordinateNode;
import api.components.map.course.transpool.graph.component.coordinate.CoordinatesManager;
import api.components.map.course.transpool.graph.component.details.StationDetailsDTO;
import api.components.map.course.transpool.graph.component.road.ArrowedEdge;
import api.components.map.course.transpool.graph.component.station.StationManager;
import api.components.map.course.transpool.graph.component.station.StationNode;
import api.components.map.course.transpool.graph.layout.MapGridLayout;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import data.transpool.TransPoolData;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MapController {

    private TransPoolController transpoolController;


    @FXML
    private JFXButton buttonPrevious;
    @FXML
    private JFXButton buttonNext;
    @FXML
    private JFXComboBox<Duration> comboboxInterval;
    @FXML
    private Label labelCurrentDay;
    @FXML
    private Label labelCurrentTime;
    @FXML
    private StackPane mapStackPane;

    private Graph mapGraph;
    private ObjectProperty<Duration> currentInteval;
    private ObjectProperty<LocalTime> currentTime;
    private IntegerProperty currentDay;

    public MapController() {
        currentInteval = new SimpleObjectProperty<>();
        currentTime = new SimpleObjectProperty<>(LocalTime.MIDNIGHT);
        currentDay = new SimpleIntegerProperty(0);
        mapGraph = new Graph();
    }

    public void setTransPoolController(TransPoolController controller) {
        this.transpoolController = controller;
    }

    @FXML
    public void initialize() {
        comboboxInterval.getItems().addAll(
                Duration.ofMinutes(5),
                Duration.ofMinutes(30),
                Duration.ofMinutes(60),
                Duration.ofHours(2),
                Duration.ofHours(24)
        );
        comboboxInterval.setValue(Duration.ofMinutes(5));
        currentInteval.bind(comboboxInterval.valueProperty());
        labelCurrentTime.textProperty().bind(currentTime.asString());
        labelCurrentDay.textProperty().bind(currentDay.asString());
        mapStackPane.getChildren().add(mapGraph.getCanvas());
    }

    @FXML
    void nextButtonAction(ActionEvent event) {
        incrementTime();
    }

    @FXML
    void previousButtonAction(ActionEvent event) {
        decrementTime();
    }

    private void incrementTime() {
        transpoolController.incrementTime(currentInteval.get());
    }

    private void decrementTime() {
        transpoolController.decrementTime(currentInteval.get());
    }

    public Graph getMap() {
        return mapGraph;
    }

    public void bindaUIToData(TransPoolData data) {
        currentTime.bind(data.getTimeDay().timeProperty());
        currentDay.bind(data.getTimeDay().dayProperty());
    }
}
