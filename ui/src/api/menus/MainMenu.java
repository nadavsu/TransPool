package api.menus;

/**
 * The main menu of the program.
 * Contains a list of menus and a title.
 */
public class MainMenu extends OptionedMenu {

    public MainMenu(String title) {
        super(title);
        this.addMenu(new LoadFileMenu("Load file"));
        this.addMenu(new NewRideRequestMenu("Create a new ride request"));
        this.addMenu(new ShowAllTranspoolTripsMenu("Show all available Transpool Trips"));
        this.addMenu(new ShowAllRideRequestsMenu("Show all ride requests"));
        this.addMenu(new FindAMatchMenu("Find a match for a ride"));
        this.addMenu(new QuitMenu("Quit."));
    }


}
