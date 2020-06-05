package api.components.card;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public abstract class CardController<T> extends ListCell<T> {
    protected FXMLLoader loader;

    protected abstract void initializeValues(T t);

    @Override
    protected void updateItem(T t, boolean empty) {
        super.updateItem(t, empty);
    }
}
