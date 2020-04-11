package api.menus;

/**
 * A menu which does an operation that will not take the user to another menu.
 * Each class inheriting from UnoptionedMenu has its own execute() function implemented differently.
 */
public abstract class UnoptionedMenu extends Menu {

    UnoptionedMenu(String title) {
        super(title);
    }

    /**
     * @see Menu#show()
     */
    @Override
    public void show() {
        title.show();
    }
}
