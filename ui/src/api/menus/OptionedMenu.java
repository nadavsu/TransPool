package api.menus;

import api.menus.components.MenuTitle;

import java.util.ArrayList;
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

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);

        show();
        System.out.print("Enter your option (1 - " + menus.size() + "): ");
        int userInput = in.nextInt();

        //Todo: add input validations.
        //System.out.println("You must enter a number between 1 and " + menus.size() + ".");

        getOption(userInput).execute();
    }


    @Override
    public void show() {
        title.show();
        System.out.println(this);
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

    public int getMenuLength() {
        return menus.size();
    }

    public Menu getOption(int i) {
        return menus.get(i - 1);
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

    public MenuTitle getTitle() {
        return title;
    }
}
