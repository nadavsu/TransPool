package api.menus;

import api.ConsoleUserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadFileMenu extends UnoptionedMenu {

    public LoadFileMenu(String title) {
        super(title);
    }

    /**
     * Gets the String of file address of the file to load from the user sends it to the engine.
     */
    @Override
    public void execute() {
        show();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean isValidInput;
        String  userStringInput;
        System.out.print("Enter your file address: ");

        do {
            try {
                isValidInput = true;
                userStringInput = in.readLine();
                //TODO: send the userStringInput to the engine and let it handle it from now on.
                //For example Engine.getInstance().loadFile(userStringInput);
            } catch (IOException iox) {
                System.out.println("Input output error!");
                isValidInput = false;
            }
        } while (!isValidInput);
    }
}
