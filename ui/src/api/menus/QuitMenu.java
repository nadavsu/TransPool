package api.menus;

import api.ConsoleUserInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuitMenu extends UnoptionedMenu {

    public QuitMenu(String title) {
        super(title);
    }

    @Override
    public void execute() {
        String userInput = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.print("Are you sure? [y/n]: ");
            try {
                userInput = in.readLine().toLowerCase();
                if (userInput.equals("y")) {
                    System.out.println("Thank you for using Transpool!");
                } else if (userInput.equals("n")) {
                    ConsoleUserInterface.getInstance().run();
                } else if (userInput.equals("y or n")) {
                    System.out.println("Very funny.");
                } else {
                    System.out.println("Invalid input. Please enter y or n.");
                }
            } catch (IOException iox) {
                System.out.print("Invalid input");
            }
        } while (!userInput.equals("y") && !userInput.equals("n"));
    }
}
