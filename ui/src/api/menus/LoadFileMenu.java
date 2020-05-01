package api.menus;

import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;
import exceptions.file.UnsupportedFileException;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadFileMenu extends UnoptionedMenu {

    public static final String SUPPORTED_DATA_FILE_TYPE = ".XML";

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
        String fileName;

        do {
            try {
                System.out.print("Enter your file address: ");
                fileName = in.readLine();

                validateFileType(fileName);
                System.out.println("Loading " + fileName + " to system...");
                engine.loadFile(fileName);
                System.out.println("File loaded successfully!");

                isValidInput = true;
            } catch (IOException | JAXBException | TransPoolDataException | InvalidTimeException e) {
                System.out.println(e.getMessage());
                isValidInput = false;
            }
        } while (!isValidInput);
    }

    private void validateFileType(String fileName) throws UnsupportedFileException {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            throw new UnsupportedFileException();
        }
        if (!fileName.substring(dotIndex).toUpperCase().equals(SUPPORTED_DATA_FILE_TYPE)) {
            throw new UnsupportedFileException();
        }
    }
}

