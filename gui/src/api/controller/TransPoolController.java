package api.controller;

import api.Engine;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

/**
    The main controller of the application.
 */
public class TransPoolController {
    private MatchTripController matchTripController;
    private MenuBarController menuBarController;
    private TransPoolTripOfferController tripOfferController;
    private TransPoolTripRequestController tripRequestController;

    private Engine engine;
    private Stage primaryStage;

    public TransPoolController() {
        this.matchTripController = new MatchTripController();
        this.menuBarController = new MenuBarController();
        this.tripOfferController = new TransPoolTripOfferController();
        this.tripRequestController = new TransPoolTripRequestController();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public MatchTripController getMatchTripController() {
        return matchTripController;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public TransPoolTripOfferController getTripOfferController() {
        return tripOfferController;
    }

    public TransPoolTripRequestController getTripRequestController() {
        return tripRequestController;
    }
}
