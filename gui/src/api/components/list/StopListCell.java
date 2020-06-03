package api.components.list;

import api.Constants;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class StopListCell extends ListCell<String> {

    @FXML private Label labelStopName;
    @FXML private JFXButton buttonDeleteStop;
    @FXML private AnchorPane anchorPaneListCellBody;

    private FXMLLoader loader;

    @Override
    public void updateItem(String stopName, boolean empty) {
        super.updateItem(stopName, empty);

        if (empty || stopName == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(Constants.STOP_LIST_CELL_RESOURCE);
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            buttonDeleteStop.setOnAction((event) -> {
                getListView().getItems().remove(getItem());
            });
            labelStopName.setText(stopName);
            setGraphic(anchorPaneListCellBody);
        }
    }
}
