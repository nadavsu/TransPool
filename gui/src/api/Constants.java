package api;

import java.net.URL;

public class Constants {
    public static final String STAGE_ICON_LOCATION = "api/resources/images/stage_icon.png";
    public static final String SPLASH_SCREEN_FXML_LOCATION = "components/splash/screen/splash_screen.fxml";
    public static final String MAIN_SCENE_FXML_LOCATION = "components/main/main_scene.fxml";
    public static final String REQUEST_CARD_FXML_LOCATION = "components/cards/request/request_card.fxml";
    public static final URL CARD_RESOURCE = Constants.class.getResource(Constants.REQUEST_CARD_FXML_LOCATION);
}
