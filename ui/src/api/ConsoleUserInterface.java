package api;

import exception.QuitOnFinishException;
import api.menu.*;
import exception.TransPoolFileNotLoadedException;
import exception.TransPoolRunTimeException;

import java.util.Scanner;

public class ConsoleUserInterface implements Runnable {

    public static void main(String[] args) {
        ConsoleUserInterface consoleUserInterface = new ConsoleUserInterface();
        consoleUserInterface.run();
    }

    public ConsoleUserInterface() {
    }

    /**
     * Runs the console user interface.
     * Creates a main menu and runs it until a QuitOnFinishException is caught.
     */
    @Override
    public void run() {
        boolean quit = false;

        Menu mainMenu = new MainMenu("Main Menu");

        do {
            try {
                mainMenu.run();
                pause();
            } catch (QuitOnFinishException e) {
                quit = true;
                System.out.println(e.getMessage());
            } catch (TransPoolFileNotLoadedException e) {
                System.out.println(e.getMessage());
            } catch (TransPoolRunTimeException e) {
                System.out.println(e.getMessage());
            }
        } while (!quit);
    }

    /**
     * Waits for user input after an operation is done on the system.
     */
    private void pause() {
        System.out.println("\r\nPress ENTER to continue.");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }
}
