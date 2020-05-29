package api.controller;

import exception.file.TransPoolDataException;
import exception.file.TransPoolFileNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;

public class MenuBarController {

    private final static String DARK_COLOR_SCHEME = "api/resources/css/scheme/color_scheme_dark.css";
    private final static String LIGHT_COLOR_SCHEME = "api/resources/css/scheme/color_scheme_light.css";

    private TransPoolController transPoolController;

    @FXML private MenuItem menuItemOpen;
    @FXML private MenuItem menuItemClose;
    @FXML private MenuItem menuItemAbout;
    @FXML private RadioMenuItem menuItemDarkMode;
    @FXML private RadioMenuItem menuItemLightMode;

    @FXML
    public void initialize() {

    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void openMenuItemAction(ActionEvent event) throws TransPoolDataException, JAXBException, TransPoolFileNotFoundException {
        transPoolController.loadFile();
    }

    @FXML
    public void helpMenuItemActionEvent(ActionEvent event) {

    }

    @FXML
    public void quitMenuItemActionEvent(ActionEvent event) {
        transPoolController.quit();
    }

    @FXML
    public void darkThemeMenuItemActionEvent(ActionEvent event) {
        transPoolController.setColorScheme(DARK_COLOR_SCHEME);
    }

    @FXML
    public void lightThemeMenuItemActionEvent(ActionEvent event) {
        transPoolController.setColorScheme(LIGHT_COLOR_SCHEME);
    }

    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load TransPool data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(transPoolController.getPrimaryStage());
        if (selectedFile == null) {
            return;
        }
        try {
            transPoolController.getEngine().loadFile(selectedFile);
            transPoolController.setFileLoaded(true);
        } catch (JAXBException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Internal Error");
            errorAlert.setContentText("There was an error loading the file (JAXB error).");
            errorAlert.showAndWait();
        } catch (TransPoolFileNotFoundException | TransPoolDataException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Data error.");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    //todo maybe move this to the engine.
    public void setColorScheme(String colorSchemeFileLocation) {
        transPoolController
                .getPrimaryStage()
                .getScene()
                .getStylesheets()
                .clear();
        transPoolController
                .getPrimaryStage()
                .getScene()
                .getStylesheets()
                .add(getClass()
                        .getResource(colorSchemeFileLocation)
                        .toExternalForm());
    }

    public void quit() {
        System.exit(0);
    }
}

