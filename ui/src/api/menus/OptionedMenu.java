package api.menus;

import exceptions.QuitOnFinishException;
import api.menus.components.MenuTitle;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/**
 * An optioned menu is a menu with more menus inside it.
 * Example - Main menu, Options menu etc.
 */
public abstract class OptionedMenu extends Menu {
    protected List<Menu> options;
    protected MenuTitle title;

    OptionedMenu(String menuName) {
        super(menuName);
        this.title = new MenuTitle(menuName);
        this.options = new ArrayList<>();
    }

    /**
     * @see Menu#run()
     */
    @Override
    public void run() throws QuitOnFinishException {
        show();
        int chosenOption = getOptionFromUser();
        getOption(chosenOption).run();
    }

    public int getOptionFromUser() throws QuitOnFinishException {
        int userIntegerInput = 1;
        boolean isValidInput;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Enter your option (1 - " + options.size() + "): ");
            try {
                isValidInput = true;
                userIntegerInput = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number between 1 and " + options.size() + ".");
                in.next();
                isValidInput = false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You must enter a number between 1 and " + options.size() + ".");
                isValidInput = false;
            }
        } while (!isValidInput);
        return userIntegerInput;
    }

    /**
     * @see Menu#show()
     */
    @Override
    public void show() {
        title.show();
        System.out.println(this);
    }

    /**
     * Adds a new menu to the list of submenus. The menu can be an OptionedMenu or an UnoptionedMenu.
     * @param menu - the menu to add to the menus list.
     */
    public void addOption(Menu menu) {
        options.add(menu);
    }

    private Menu getOption(int i) {
        return options.get(i - 1);
    }

    public MenuTitle getTitle() {
        return title;
    }

    public int getMenuLength() {
        return options.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < options.size(); i++) {
            stringBuilder.append(i + 1).append(". ");
            stringBuilder.append(options.get(i).getMenuName()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
