package api.menu;

/**
 * A plain option
 */
public class Option extends Menu {

    Option(String menuName) {
        super(menuName);
    }

    /**
     * Shows the option
     */
    @Override
    public void run() {
        showMenu();
    }

    /**
     * Prints the menu name to the console.
     */
    @Override
    public void showMenu() {
        System.out.println(menuName);
    }
}
