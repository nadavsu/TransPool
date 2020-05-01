package api.menus;

import api.Engine;

public class ShowAllTripRequestsMenu extends UnoptionedMenu {
    public ShowAllTripRequestsMenu(String title) {
        super(title);
    }

    @Override
    public void run(){
        try {
            engine.showAllTransPoolTripRequests();
        } catch (NullPointerException e) {
            System.out.println("No trip requests found at the moment.");
        }
    }
}
