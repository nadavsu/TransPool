package api.controller;

import api.components.FileEngine;
import exception.file.TransPoolDataException;
import exception.file.TransPoolFileNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;

public class MenuBarController extends Controller {

    @FXML private MenuItem menuItemOpen;
    @FXML private MenuItem menuItemClose;
    @FXML private MenuItem menuItemDarkMode;
    @FXML private MenuItem menuItemLightMode;
    @FXML private MenuItem menuItemAbout;

    private SimpleStringProperty selectedFileProperty;
    private FileEngine engine;

    public MenuBarController() {
        selectedFileProperty = new SimpleStringProperty();
    }

    public void setEngine(FileEngine engine) {

    }

    @FXML
    public void openMenuItemAction(ActionEvent event) throws TransPoolDataException, JAXBException, TransPoolFileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load TransPool data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        selectedFileProperty.set(selectedFile.getAbsolutePath());
        engine.loadData(selectedFile);
    }

    @FXML
    public void helpMenuItemActionEvent(ActionEvent event) {

    }

    @FXML
    public void quit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void setDarkTheme(ActionEvent event) {
       // primaryStage.getScene()
       //         .getStylesheets()
       //         .add(getClass().getResource(ResourcesConstants.DARK_THEME_LOCATION).toExternalForm());
    }

    @FXML
    public void setLightTheme(ActionEvent event) {
      //  primaryStage.getScene()
      //          .getStylesheets()
       //         .add(getClass().getResource(ResourcesConstants.LIGHT_THEME_LOCATION).toExternalForm());
    }
}

