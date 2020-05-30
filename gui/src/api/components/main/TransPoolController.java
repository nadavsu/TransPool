package api.components.main;

import api.Engine;
import api.components.trips.bar.Clearable;
import api.components.data.bar.DataBarController;
import api.components.menu.bar.MenuBarController;
import api.components.trips.bar.match.MatchTripController;
import api.components.trips.bar.offer.TransPoolTripOfferController;
import api.components.trips.bar.request.TransPoolTripRequestController;
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

    private Stage primaryStage;
    private Engine engine;

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
            tripOfferComponentController.setTranspoolController(this);
            tripRequestComponentController.setTransPoolController(this);
            menuBarComponentController.setTransPoolController(this);
            dataBarComponentController.setTransPoolController(this);
        }

        fileLoaded.bindBidirectional(matchTripComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripOfferComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripRequestComponentController.fileLoadedProperty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        fileLoaded.bind(this.engine.fileLoadedProperty());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Engine getEngine() {
        return engine;
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
        dataBarComponentController.updateData();
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

    public void clearForm(Clearable form) {
        form.clear();
    }
}
