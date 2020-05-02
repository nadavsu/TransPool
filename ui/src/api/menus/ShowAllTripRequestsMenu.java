package api.menus;

public class ShowAllTripRequestsMenu extends InputMenu {
    public ShowAllTripRequestsMenu(String title) {
        super(title);
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
