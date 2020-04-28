package api;

import exceptions.QuitOnFinishException;
import api.menus.*;

public class ConsoleUserInterface implements Runnable {

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
