package api.menus;

import api.exceptions.QuitOnFinishException;
import api.menus.components.MenuTitle;

import java.io.IOException;

public abstract class Menu {

    protected MenuTitle title;

    public Menu(String title) {
        this.title = new MenuTitle(title);
    }

    /**
     * Runs the current menu's operations such as getting data from the user or entering another menu.
     * Includes user input validations
     * In an OptionedMenu - this function will call the execute() function of the chosen submenu. All optioned menus
     *                      have the same execute() function.
     * In an UnoptionedMenu - each menu will have a different execute() function.
     *
     * @throws QuitOnFinishException - A menu can throw a QuitOnFinishException to signal the program to stop running
     *                                 when finished executing the menu.
     */
    public abstract void execute() throws QuitOnFinishException;

    /**
     * Prints to the console the menu in the proper format.
     */
    public abstract void show();

}
