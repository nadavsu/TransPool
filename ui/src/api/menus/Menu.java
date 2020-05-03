package api.menus;

import api.Engine;
import exceptions.QuitOnFinishException;
import exceptions.TransPoolFileNotLoadedException;

public abstract class Menu {

    protected String menuName;

    public Menu(String menuName)
    {
        this.menuName = menuName;
    }

     /**
     * Runs the current menu's operations such as getting data from the data.transpool.user or entering another menu.
     * Includes data.transpool.user input validations
     * In an OptionedMenu - this function will call the run function of the chosen submenu. All optioned menus
     *                      have the same run function.
     * In an UnoptionedMenu - each menu will have a different run function.
     *
     * @throws QuitOnFinishException - A menu can throw a QuitOnFinishException to signal the program to stop running
     *                                 when finished running the menu.
     */
    public abstract void run() throws QuitOnFinishException, TransPoolFileNotLoadedException;

    /**
     * Prints to the console the menu in the proper format.
     */
    public abstract void show();

    public String getMenuName() {
        return menuName;
    }
}
