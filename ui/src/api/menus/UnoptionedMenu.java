package api.menus;

/**
 * A menu which does an operation that will not take the user to another menu.
 */
public abstract class UnoptionedMenu extends Menu {

    UnoptionedMenu(String title) {
        super(title);
    }

    @Override
    public void show() {
        title.show();
    }
}
