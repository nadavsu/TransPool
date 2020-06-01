package api.components;

import javafx.application.Platform;

import java.util.function.Consumer;


//todo: shouldn't be generic type, T should be some generic card.
public class UICardAdapter<T> {
    private Consumer<T> introduceNewCard;
    private Consumer<T> updateCard;

    public UICardAdapter(Consumer<T> introduceNewCard, Consumer<T> updateCard) {
        this.introduceNewCard = introduceNewCard;
        this.updateCard = updateCard;
    }

    public void addCard(T tripData) {
        Platform.runLater(() -> {
            introduceNewCard.accept(tripData);
        });
    }

    public void updateExistingCard(T tripData) {
        Platform.runLater(() -> {
            updateCard.accept(tripData);
        });
    }
}

