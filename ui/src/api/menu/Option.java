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
        show();
    }

    /**
     * Prints the menu name to the console.
     */
    @Override
    public void show() {
        System.out.println(menuName);
    }
}
