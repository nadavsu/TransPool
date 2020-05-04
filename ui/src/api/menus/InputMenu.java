package api.menus;


import api.menus.components.MenuTitle;

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
        this.sc = new Scanner(System.in);

    }

    /**
     * @see Menu#showMenu()
     */
    @Override
    public void showMenu() {
        title.show();
    }

    /**
     * Functions that take input from the user.
     * @param message
     * @return The user's input.
     */
    public String getStringFromUser(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    /**
     * @see InputMenu#getStringFromUser(String)
     */
    public int getIntegerFromUser(String message) {
        System.out.print(message);
        return sc.nextInt();
    }

    /**
     * @see InputMenu#getStringFromUser(String)
     */
    public boolean getBooleanFromUser(String message){
        System.out.print(message);
        sc.nextLine();
        return sc.nextLine().toLowerCase().equals("y");
    }
}