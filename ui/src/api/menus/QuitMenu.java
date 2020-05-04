package api.menus;

import exceptions.QuitOnFinishException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuitMenu extends InputMenu {

    public QuitMenu(String title) {
        super(title);
    }


    /**
     * Gets input from the user whether he wants to really quit or not
     * @throws QuitOnFinishException - If the user wants to quit.
     */
    @Override
    public void run() throws QuitOnFinishException {
        String userInput;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.print("Are you sure? [y/n]: ");
            try {
                userInput = in.readLine().toLowerCase();
                switch (userInput) {
                    case "y":
                        throw new QuitOnFinishException();
                    case "n":
                        return;
                    case "y or n":
                        System.out.println("Very funny.");
                        break;
                    default:
                        System.out.println("Invalid input. Please enter y or n.");
                        break;
                }
            } catch (IOException iox) {
                System.out.print("Input output error!");
            }
        } while (true);
    }
}
