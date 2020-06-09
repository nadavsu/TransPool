package api.components;

import api.Engine;
import api.components.data.bar.DataBarController;
import api.components.form.Form;
import api.components.form.feedback.FeedbackFormController;
import api.components.menu.bar.MenuBarController;
import api.components.form.match.MatchTripFormController;
import api.components.form.offer.TripOfferFormController;
import api.components.form.request.TripRequestFormController;
import api.exception.RequiredFieldEmptyException;
import data.transpool.TransPoolData;
import data.transpool.trip.offer.PossibleMatch;
import data.transpool.trip.request.TripRequest;
import data.transpool.trip.request.TripRequestData;
import exception.NoMatchesFoundException;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;


/**
    The main controller of the application.
 */
public class TransPoolController {

    private Stage primaryStage;
    private Engine engine;

    @FXML private MatchTripFormController matchTripComponentController;
    @FXML private MenuBarController menuBarComponentController;
    @FXML private TripOfferFormController tripOfferComponentController;
    @FXML private TripRequestFormController tripRequestComponentController;
    @FXML private DataBarController dataBarComponentController;
    @FXML private FeedbackFormController feedbackComponentController;

    @FXML private MenuBar menuBarComponent;
    @FXML private AnchorPane matchTripComponent;
    @FXML private AnchorPane tripOfferComponent;
    @FXML private AnchorPane tripRequestComponent;
    @FXML private VBox dataBarComponent;

    private BooleanProperty fileLoaded;
    private StringProperty currentTaskProgress;

    public TransPoolController() {
        fileLoaded = new SimpleBooleanProperty(false);
        currentTaskProgress = new SimpleStringProperty("");
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
            dataBarComponentController.setTransPoolController(this);
            feedbackComponentController.setTransPoolController(this);
        }

        fileLoaded.bindBidirectional(menuBarComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(matchTripComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripOfferComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripRequestComponentController.fileLoadedProperty());
        currentTaskProgress.bindBidirectional(dataBarComponentController.currentTaskProgressProperty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        fileLoaded.bind(this.engine.fileLoadedProperty());
    }

    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                  LocalTime time, boolean isArrivalTime, boolean isContinuous){
        try {
            engine.createNewTransPoolTripRequest(riderName, source, destination, time, isArrivalTime, isContinuous);
        } catch (TransPoolDataException e) {
            showAlert(e);
        }
    }

    public void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, String recurrences,
                                   int riderCapacity, int PPK, ObservableList<String> addedStops) {
        try {
            engine.createNewTripOffer(driverName, departureTime, dayStart, recurrences, riderCapacity, PPK, addedStops);
        } catch (TransPoolDataException e) {
            showAlert(e);
        }

    }

    public void createNewMatch(PossibleMatch tripOffer) {
        engine.addNewMatch(tripOffer);
    }

    public void findPossibleMatches(TripRequest requestToMatch, int numOfResults) {
        try {
            engine.findPossibleMatches(requestToMatch, numOfResults);
        } catch (NoMatchesFoundException | TransPoolDataException e) {
            showAlert(e);
        }
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
    }


    public void setColorScheme(String colorSchemeFileLocation) {
        menuBarComponentController.setColorScheme(colorSchemeFileLocation);
    }

    public void quit() {
        menuBarComponentController.quit();
    }

    //---------------------------------------------------------------------------------------------//

    public void clearForm(Form form) {
        form.clear();
    }

    public void submitForm(Form form) {
        try {
            if (form.isValid()) {
                form.submit();
                form.clear();
            } else {
                throw new RequiredFieldEmptyException();
            }
        } catch(RequiredFieldEmptyException e) {
            showAlert(e);
        }
    }

    public void bindTaskToUI(Task currentRunningTask) {
        dataBarComponentController.bindTaskToUI(currentRunningTask);
    }

    public void bindUIToData(TransPoolData data) {
        dataBarComponentController.bindUIToData(data);
        matchTripComponentController.bindUIToData(data);
        tripOfferComponentController.bindDataToUI(data);
        feedbackComponentController.bindUIToData(data);
    }

    public void showAlert(Exception e) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        errorAlert.setHeaderText(null);
        errorAlert.showAndWait();
    }

    public ObservableList<Integer> getAllMatchedTripRequestIDs() {
        return engine.getAllMatchedTripRequestIDs();
    }

    public void initiateFeedbackEngine(int riderID) {
        engine.initiateFeedbackEngine(riderID);
    }
}
