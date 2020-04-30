package api.menus;

import api.Engine;

public class ShowAllTranspoolTripsMenu extends UnoptionedMenu {

    public ShowAllTranspoolTripsMenu(String title) {
        super(title);
    }

    @Override
    public void run() {
        try {
            Engine.getInstance()
                    .getData()
                    .getTransPoolTrips()
                    .forEach(System.out::println);
        } catch (NullPointerException e) {
            System.out.println("No TransPool trips found at the moment.");
        }
    }
}
