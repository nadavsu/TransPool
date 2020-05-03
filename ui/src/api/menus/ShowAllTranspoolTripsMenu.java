package api.menus;

import api.Engine;

public class ShowAllTranspoolTripsMenu extends InputMenu {

    private Engine engine;

    public ShowAllTranspoolTripsMenu(String title) {
        super(title);
        engine = new Engine();
    }

    @Override
    public void run() {
        try {
            show();
            System.out.println(engine.getAllTransPoolTrips());
        } catch (NullPointerException e) {
            System.out.println("No TransPool trips found at the moment.");
        }
    }
}
