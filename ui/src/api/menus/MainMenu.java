package api.menus;

/**
 * The main menu of the program.
 * Contains a list of menus and a title.
 * The user will choose one of these submenus in the superclass' (OptionedMenu) run function.
 */
public class MainMenu extends OptionedMenu {

    public MainMenu(String title) {
        super(title);
        this.addOption(new LoadFileMenu("Load file"));
        this.addOption(new NewTripRequestMenu("Create a new ride request"));
        this.addOption(new ShowAllTranspoolTripsStatusMenu("Show all available TransPool Trips"));
        this.addOption(new ShowAllTripRequestsMenu("Show all ride requests"));
        this.addOption(new FindAMatchMenu("Find a match for a ride"));
        this.addOption(new QuitMenu("Quit."));
        this.addOption(new _debugfill("debug fill"));
    }


}
