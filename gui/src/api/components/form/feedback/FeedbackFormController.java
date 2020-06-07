package api.components.form.feedback;

import api.components.form.FormController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import data.transpool.TransPoolData;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FeedbackFormController extends FormController {

    @FXML private JFXComboBox<Integer> comboBoxRideID;
    @FXML private JFXButton buttonShowDrivers;
    @FXML private JFXTextArea textAreaComment;
    @FXML private JFXListView<String> listViewDrivers;
    @FXML private JFXComboBox<Integer> comboboxRating;
    @FXML private JFXButton buttonAddFeedback;

    private BooleanProperty foundResults;

    public FeedbackFormController() {
        foundResults = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        super.initialize();
        comboboxRating.getItems().addAll(1, 2, 3, 4, 5);
        buttonAddFeedback.disableProperty().bind(foundResults);
    }

    @FXML
    public void createNewFeedbackButtonAction(ActionEvent event) {

    }

    @FXML
    public void searchDriversButtonAction(ActionEvent event) {
        transpoolController.initiateFeedbackEngine(comboBoxRideID.getValue());
    }

    @Override
    public void clear() {

    }

    @Override
    public void setValidations() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void submit() {

    }

    public void bindUIToData(TransPoolData data) {
        comboBoxRideID.setItems(transpoolController.getAllMatchedTripRequestIDs());
    }
}
