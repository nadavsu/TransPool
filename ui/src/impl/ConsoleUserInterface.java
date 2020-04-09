package impl;

import impl.menus.*;

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
        OptionedMenu mainMenu = new MainMenu("Transpool");          //Make this of type Menu.
        mainMenu.execute();
    }

    private boolean userInputIsValid(int userInput, Menu menu) {
        return true;
    }
}
