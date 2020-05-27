package api.menu;

import exception.TransPoolFileNotLoadedException;

public class ShowAllTranspoolTripsStatusMenu extends InputMenu {

    private Engine engine;

    public ShowAllTranspoolTripsStatusMenu(String title) {
        super(title);
        engine = new Engine();
    }

    /**
     * Shows the status of all offered TransPool trips.
     * Gets the list of TransPool trips from the engine and prints it as a string to the console.
     * @throws TransPoolFileNotLoadedException - If no file is loaded.
     */
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
