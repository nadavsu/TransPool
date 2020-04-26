package api.menus;

import api.Engine;
import validators.Constants;
import validators.Validator;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadFileMenu extends UnoptionedMenu {

    public LoadFileMenu(String title) {
        super(title);
    }

    /**
     * Gets the String of file address of the file to load from the data.transpool.user sends it to the engine.
     */
    @Override
    public void run() {
        show();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean isValidInput;
        String fileAddress;

        do {
            try {
                Validator validator = new Validator();

                System.out.print("Enter your file address: ");
                fileAddress = in.readLine();
                validator.validateFileType(fileAddress, Constants.SUPPORTED_DATA_FILE_TYPE);

                System.out.println("Loading " + fileAddress + " to system...");
                Engine.getInstance().loadFile(fileAddress);
                System.out.println("File loaded successfully!");

                isValidInput = true;
            } catch (IOException | JAXBException e) {
                System.out.println(e.getMessage());
                isValidInput = false;
            }
        } while (!isValidInput);
    }
}

