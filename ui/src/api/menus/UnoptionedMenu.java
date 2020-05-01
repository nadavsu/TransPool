package api.menus;


import api.Engine;

import java.util.Scanner;

/**
 * A menu which does an operation that will not take the data.transpool.user to another menu.
 * Each class inheriting from UnoptionedMenu has its own run function implemented differently.
 */
public abstract class UnoptionedMenu extends Menu {

    Engine engine;
    UnoptionedMenu(String title) {
        super(title);
        engine = new Engine();
    }

    /**
     * @see Menu#show()
     */
    @Override
    public void show() {
        title.show();
    }
}
