package api.controller;

import api.Engine;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
    The main controller of the application.
 */
public class TransPoolController {

    @FXML private MatchTripController matchTripComponentController;
    @FXML private MenuBarController menuBarComponentController;
    @FXML private TransPoolTripOfferController tripOfferComponentController;
    @FXML private TransPoolTripRequestController tripRequestComponentController;
    @FXML private DataBarController dataBarComponentController;

    @FXML private MenuBar menuBarComponent;
    @FXML private AnchorPane matchTripComponent;
    @FXML private AnchorPane tripOfferComponent;
    @FXML private AnchorPane tripRequestComponent;
    @FXML private VBox dataBarComponent;

    private Engine transpoolEngine;
    private Stage  primaryStage;

    private BooleanProperty fileLoaded;

    public TransPoolController() {
        fileLoaded = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        if (matchTripComponentController != null
                && menuBarComponentController != null
                && tripOfferComponentController != null
                && tripRequestComponentController != null) {
            matchTripComponentController.setTransPoolController(this);
            tripOfferComponentController.setTransPoolController(this);
            tripRequestComponentController.setTransPoolController(this);
            menuBarComponentController.setTransPoolController(this);
        }

        fileLoaded.bindBidirectional(matchTripComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripOfferComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripRequestComponentController.fileLoadedProperty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(Engine engine) {
        transpoolEngine = engine;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Engine getEngine() {
        return transpoolEngine;
    }


    //---------------------------------------------------------------------------------------------//

    public boolean isFileLoaded() {
        return fileLoaded.get();
    }

    public void setFileLoaded(boolean value) {
        fileLoaded.set(value);
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }

    //---------------------------------------------------------------------------------------------//

    public void loadFile() {
        menuBarComponentController.loadFile();
    }

    public void setColorScheme(String colorSchemeFileLocation) {
        menuBarComponentController.setColorScheme(colorSchemeFileLocation);
    }

    public void quit() {
        menuBarComponentController.quit();
    }

    //---------------------------------------------------------------------------------------------//

    public void addNewTripRequest() {
        tripRequestComponentController.addNewTripRequest();
    }

    //---------------------------------------------------------------------------------------------//

    public void clearForm(FormController form) {
        form.clearForm();
    }
}
