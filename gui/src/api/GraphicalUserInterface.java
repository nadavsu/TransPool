package api;

import api.components.FileEngine;
import api.controller.TransPoolController;
import api.resources.ResourcesConstants;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;

public class GraphicalUserInterface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TransPool");
        primaryStage.getIcons().add(new Image(ResourcesConstants.STAGE_ICON_LOCATION));

        FXMLLoader splashScreenLoader = new FXMLLoader();
        FXMLLoader mainLoader = new FXMLLoader();
        URL splashScreenFXML = getClass().getResource(ResourcesConstants.SPLASH_SCREEN_FXML_LOCATION);
        URL mainFXML = getClass().getResource(ResourcesConstants.MAIN_SCENE_FXML_LOCATION);

        //Loading the splash screen
        splashScreenLoader.setLocation(splashScreenFXML);
        Parent splashScreenRoot = splashScreenLoader.load();
        Scene splashScreenScene = new Scene(splashScreenRoot, 1080, 720);
        primaryStage.setScene(splashScreenScene);
        primaryStage.show();

        //Loading the main FXML scene
        mainLoader.setLocation(mainFXML);
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot, 1080, 720);

        //Setting up the controller
        TransPoolController transpoolController = mainLoader.getController();
        TransPoolEngine engine = new TransPoolEngine(transpoolController);
        transpoolController.setEngine(engine);
        transpoolController.setPrimaryStage(primaryStage);

        //Pause transition for the splash screen. When pause is finished the scene is switched to the main scene.
        PauseTransition splashScreenPause = new PauseTransition(Duration.millis(2300));
        splashScreenPause.setOnFinished(event -> primaryStage.setScene(mainScene));
        splashScreenPause.play();
    }

    public static void main(String[] args) {
        launch(GraphicalUserInterface.class);
    }
}
