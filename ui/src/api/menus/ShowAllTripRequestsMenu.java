package api.menus;

import api.Engine;
import exceptions.TransPoolFileNotLoadedException;

import java.util.List;

public class ShowAllTripRequestsMenu extends InputMenu {
    private Engine engine;
    public ShowAllTripRequestsMenu(String title) {
        super(title);
        engine = new Engine();

    }

    @Override
    public void run() throws TransPoolFileNotLoadedException {
        if (!engine.isFileLoaded()) {
            throw new TransPoolFileNotLoadedException();
        }

        try {
            show();
            List<String> allTransPoolTrips = engine.getAllTransPoolTripRequestsAsStrings();
            allTransPoolTrips.forEach(System.out::println);
        } catch (NullPointerException e) {
            System.out.println("No trip requests found at the moment.");
        }
    }
}
