package api;

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
        Menu mainMenu = new MainMenu("TransPool!");
        mainMenu.execute();
    }

}
