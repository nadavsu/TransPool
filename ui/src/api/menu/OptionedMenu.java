package api.menu;

import exception.InvalidOptionException;
import api.menu.component.MenuTitle;
import exception.TransPoolFileNotLoadedException;

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
    protected int chosenOption;

    OptionedMenu(String menuName) {
        super(menuName);
        this.title = new MenuTitle(menuName);
        this.options = new ArrayList<>();
    }

    /**
     * @see Menu#run()
     */
    @Override
    public void run() throws TransPoolFileNotLoadedException {
        showMenu();
        getOptionFromUser();
        options.get(chosenOption).run();
    }

    public void getOptionFromUser() throws IndexOutOfBoundsException {
        int userIntegerInput = 1;
        boolean isValidInput;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Enter your option (1 - " + options.size() + "): ");
            try {
                userIntegerInput = in.nextInt();
                validateOptionInput(userIntegerInput);
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number between 1 and " + options.size() + ".");
                in.next();
                isValidInput = false;
            } catch (InvalidOptionException e) {
                System.out.println("You must enter a number between 1 and " + options.size() + ".");
                isValidInput = false;
            }

        } while (!isValidInput);
        chosenOption = userIntegerInput - 1;
    }

    private void validateOptionInput(int userInput) throws InvalidOptionException {
        if (userInput > options.size() || userInput <= 0) {
            throw new InvalidOptionException();
        }
    }

    /**
     * @see Menu#showMenu()
     */
    @Override
    public void showMenu() {
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
