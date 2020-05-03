package api.menus;

import api.Engine;

public class ShowAllTripRequestsMenu extends InputMenu {
    private Engine engine;
    public ShowAllTripRequestsMenu(String title) {
        super(title);
        engine = new Engine();

    }

    @Override
    public void run(){
        try {
            show();
            System.out.println(engine.getAllTransPoolTripRequests());
        } catch (NullPointerException e) {
            System.out.println("No trip requests found at the moment.");
        }
    }
}
