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
    protected List<Menu> menus;

    OptionedMenu(String title) {
        super(title);
        this.menus = new ArrayList<>();
    }

    /**
     * @see Menu#run()
     */
    @Override
    public void run() throws QuitOnFinishException {
        show();
        int userIntegerInput;
        boolean isValidInput;
        Scanner in = new Scanner(System.in);

        do {
            System.out.print("Enter your option (1 - " + menus.size() + "): ");
            try {
                isValidInput = true;
                userIntegerInput = in.nextInt();
                getOption(userIntegerInput).run();
                pause();
            } catch (InputMismatchException e) {
                System.out.println("You must enter a number between 1 and " + menus.size() + ".");
                in.next();
                isValidInput = false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You must enter a number between 1 and " + menus.size() + ".");
                isValidInput = false;
            }
        } while (!isValidInput) ;
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
    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    private Menu getOption(int i) {
        return menus.get(i - 1);
    }

    public MenuTitle getTitle() {
        return title;
    }

    public int getMenuLength() {
        return menus.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < menus.size(); i++) {
            stringBuilder.append(i + 1).append(". ");
            stringBuilder.append(menus.get(i).title.getTitleName()).append("\r\n");
        }
        return stringBuilder.toString();
    }

    private void pause() {
        System.out.println("\r\nPress ENTER to continue.");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
