package api.menus;


import api.Engine;
import api.menus.components.MenuTitle;
import exceptions.QuitOnFinishException;

import java.util.Scanner;

/**
 * A menu which does an operation that will not take the user to another menu.
 * Each class inheriting from InputMenu has its own run function implemented differently.
 */
public abstract class InputMenu extends Menu {
    protected MenuTitle title;
    protected Scanner sc;

    InputMenu(String menuName) {
        super(menuName);
        this.title = new MenuTitle(menuName);
        this.engine = new Engine();
        this.sc = new Scanner(System.in);

    }

    /**
     * @see Menu#show()
     */
    @Override
    public void show() {
        title.show();
    }

    public String getStringFromUser(String message) {
        System.out.println(message);
        return sc.nextLine().trim();
    }

    public int getIntegerFromUser(String message) {
        System.out.println(message);
        return sc.nextInt();
    }

    public boolean getBooleanFromUser(String message){
        System.out.println(message);
        return sc.nextLine().toLowerCase().equals("y");
    }
}