package api;

import java.net.URL;

public class Constants {
    public static final String STAGE_ICON_LOCATION = "api/resources/images/stage_icon.png";
    public static final String SPLASH_SCREEN_FXML_LOCATION = "components/splash/screen/splash_screen.fxml";
    public static final String MAIN_SCENE_FXML_LOCATION = "components/main/main_scene.fxml";
    public static final String REQUEST_CARD_FXML_LOCATION = "components/card/request/request_card.fxml";
    public static final String OFFER_CARD_FXML_LOCATION = "components/card/offer/offer_card.fxml";
    public static final String STOP_LIST_CELL_FXML_LOCATION = "components/list/stop_list_cell.fxml";
    public static final URL TRIP_REQUEST_CARD_RESOURCE = Constants.class.getResource(Constants.REQUEST_CARD_FXML_LOCATION);
    public static final URL TRIP_OFFER_CARD_RESOURCE = Constants.class.getResource(Constants.OFFER_CARD_FXML_LOCATION);
    public static final URL STOP_LIST_CELL_RESOURCE = Constants.class.getResource(Constants.STOP_LIST_CELL_FXML_LOCATION);
}
