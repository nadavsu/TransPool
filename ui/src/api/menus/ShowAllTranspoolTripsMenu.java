package api.menus;

public class ShowAllTranspoolTripsMenu extends UnoptionedMenu {

    public ShowAllTranspoolTripsMenu(String title) {
        super(title);
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
