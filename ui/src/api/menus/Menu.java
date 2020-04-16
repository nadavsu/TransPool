package api.menus;

import exceptions.system.QuitOnFinishException;
import api.menus.components.MenuTitle;

public abstract class Menu {

    protected MenuTitle title;

    public Menu(String title) {
        this.title = new MenuTitle(title);
    }

     /**
     * Runs the current menu's operations such as getting data from the user or entering another menu.
     * Includes user input validations
     * In an OptionedMenu - this function will call the run function of the chosen submenu. All optioned menus
     *                      have the same run function.
     * In an UnoptionedMenu - each menu will have a different run function.
     *
     * @throws QuitOnFinishException - A menu can throw a QuitOnFinishException to signal the program to stop running
     *                                 when finished running the menu.
     */
    public abstract void run() throws QuitOnFinishException;

    /**
     * Prints to the console the menu in the proper format.
     */
    public abstract void show();

}
