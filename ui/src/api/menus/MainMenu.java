package api.menus;

/**
 * The main menu of the program.
 * Contains a list of menus and a title.
 * The user will choose one of these submenus in the superclass' (OptionedMenu) run function.
 */
public class MainMenu extends OptionedMenu {

    public MainMenu(String title) {
        super(title);
        this.addMenu(new LoadFileMenu("Load file"));
        this.addMenu(new NewTripRequestMenu("Create a new ride request"));
        this.addMenu(new ShowAllTranspoolTripsMenu("Show all available TransPool Trips"));
        this.addMenu(new ShowAllTripRequestsMenu("Show all ride requests"));
        this.addMenu(new FindAMatchMenu("Find a match for a ride"));
        this.addMenu(new QuitMenu("Quit."));
    }


}
