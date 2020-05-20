package api;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GraphicalUserInterface extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TransPool");
        primaryStage.getIcons().add(new Image("api/resources/images/stage_icon.png"));

        Parent load = FXMLLoader.load(getClass().getResource("resources/fxml/splash_screen.fxml"));
        Scene splashScreenScene = new Scene(load, 1080, 720);
        primaryStage.setScene(splashScreenScene);
        primaryStage.show();

        load = FXMLLoader.load(getClass().getResource("resources/fxml/main_scene.fxml"));
        Scene mainScene = new Scene(load, 1080, 720);

        PauseTransition splashScreenPause = new PauseTransition(Duration.millis(2300));
        splashScreenPause.setOnFinished(event -> primaryStage.setScene(mainScene));
        splashScreenPause.play();
    }

    public static void main(String[] args) {
        launch(GraphicalUserInterface.class);
    }
}
