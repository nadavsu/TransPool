package api.menus;

import api.menus.components.MenuTitle;

import java.io.IOException;

public abstract class Menu {

    protected MenuTitle title;

    public Menu(String title) {
        this.title = new MenuTitle(title);
    }

    /**
     * Runs the current menu's operations such as getting data from the user or entering another menu.
     */
    public abstract void execute();

    /**
     * Prints to the console the menu in the proper menu format.
     */
    public abstract void show();

}
