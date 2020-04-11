package api;

import api.exceptions.QuitOnFinishException;
import api.menus.*;

public class ConsoleUserInterface {

    private static ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();

    public static void main(String[] args) {
        consoleUserInterface.run();
    }

    private ConsoleUserInterface() {

    }

    public static ConsoleUserInterface getInstance() {
        if (consoleUserInterface == null) {
            consoleUserInterface = new ConsoleUserInterface();
        }
        return consoleUserInterface;
    }

    public void run() {
        boolean quit = false;

        Menu mainMenu = new MainMenu("Main Menu");

        do {
            try {
                mainMenu.execute();
            } catch (QuitOnFinishException e) {
                quit = true;
                System.out.println(e.getMessage());
            }
        } while (!quit);
    }
}
