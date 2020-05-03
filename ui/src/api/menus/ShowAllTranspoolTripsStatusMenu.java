package api.menus;

import api.Engine;
import exceptions.TransPoolFileNotLoadedException;

public class ShowAllTranspoolTripsStatusMenu extends InputMenu {

    private Engine engine;

    public ShowAllTranspoolTripsStatusMenu(String title) {
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
            System.out.println(engine.getAllTransPoolTrips());
        } catch (NullPointerException e) {
            System.out.println("No TransPool trips found at the moment.");
        }
    }
}
