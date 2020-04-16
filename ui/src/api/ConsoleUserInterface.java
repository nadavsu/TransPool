package api;

import exceptions.system.QuitOnFinishException;
import api.menus.*;

public class ConsoleUserInterface implements Runnable {

    public static void main(String[] args) {
        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();
        consoleUserInterface.run();
    }

    public ConsoleUserInterface() {
    }

    @Override
    public void run() {
        boolean quit = false;

        Menu mainMenu = new MainMenu("Main Menu");

        do {
            try {
                mainMenu.run();
            } catch (QuitOnFinishException e) {
                quit = true;
                System.out.println(e.getMessage());
            }
        } while (!quit);
    }
}
