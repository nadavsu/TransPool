package api.menus;

import java.util.Scanner;

public class LoadFileMenu extends UnoptionedMenu {

    public LoadFileMenu(String title) {
        super(title);
    }

    @Override
    public void execute() {
        Scanner in = new Scanner(System.in);
        show();
        System.out.println("Enter your file address: ");
    }
}
