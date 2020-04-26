package api.menus;


import java.util.Scanner;

/**
 * A menu which does an operation that will not take the data.transpool.user to another menu.
 * Each class inheriting from UnoptionedMenu has its own run function implemented differently.
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
