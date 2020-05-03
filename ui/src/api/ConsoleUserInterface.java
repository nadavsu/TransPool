package api;

import api.menus.components.Constants;
import exceptions.QuitOnFinishException;
import api.menus.*;
import exceptions.TransPoolFileNotLoadedException;

import java.util.Scanner;

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
                pause();
            } catch (QuitOnFinishException e) {
                quit = true;
                System.out.println(e.getMessage());
            } catch (TransPoolFileNotLoadedException e) {
                System.out.println(e.getMessage());
            }
        } while (!quit);
    }

    private void pause() {
        System.out.println("\r\nPress ENTER to continue.");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static void printLine() {
        for (int i = 0; i < Constants.MENU_WIDTH; i++) {
            System.out.print("-");
        }
        System.out.print("\n");
    }
}
