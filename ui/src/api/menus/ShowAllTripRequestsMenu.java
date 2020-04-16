package api.menus;

import api.Engine;

public class ShowAllTripRequestsMenu extends UnoptionedMenu {
    public ShowAllTripRequestsMenu(String title) {
        super(title);
    }

    @Override
    public void run(){
        try {
            Engine.getInstance().showAllTripRequests();
        } catch (NullPointerException e) {
            System.out.println("No trip requests found at the moment.");
        }
    }
}
