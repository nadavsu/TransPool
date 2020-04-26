package api.menus;

import api.Engine;

public class ShowAllTranspoolTripsMenu extends UnoptionedMenu {

    public ShowAllTranspoolTripsMenu(String title) {
        super(title);
    }

    @Override
    public void run() {
        try {
            Engine.getInstance().showAllTranspoolTrips();
        } catch (NullPointerException e) {
            System.out.println("No TransPool data.transpool.trips found at the moment.");
        }
    }
}
