package impl.menus;

public class MainMenu extends OptionedMenu {

    public MainMenu(String title) {
        super(title);
        this.addMenu(new LoadFileOperation("Load file"));
        this.addMenu(new NewRideRequestOperation("Create a new ride request"));
        this.addMenu(new ShowAllTranspoolTripsOperation("Show all available Transpool Trips"));
        this.addMenu(new ShowAllRideRequestsOperation("Show all ride requests"));
        this.addMenu(new FindAMatchOperation("Find a match for a ride"));
        this.addMenu(new QuitOperation("Quit."));
    }


}
