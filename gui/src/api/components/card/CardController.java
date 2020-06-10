package api.components.card;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public abstract class CardController<T> extends ListCell<T> {
    protected FXMLLoader loader;

    @FXML protected AnchorPane anchorPaneCardBody;

    protected abstract void initializeValues(T t);
    protected abstract void loadCard();

    @Override
    protected void updateItem(T cardType, boolean empty) {
        super.updateItem(cardType, empty);

        if (empty || cardType == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loadCard();
            }

            initializeValues(cardType);

            setText(null);
            setGraphic(anchorPaneCardBody);
        }
    }
}
