package api.menus;

import api.FileEngine;
import exceptions.UnsupportedFileException;
import exceptions.file.TransPoolFileDataException;
import exceptions.time.InvalidTimeException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class LoadFileMenu extends InputMenu {

    private static final String SUPPORTED_DATA_FILE_TYPE = ".XML";
    private FileEngine engine;
    public LoadFileMenu(String title) {
        super(title);
    }

    /**
     * Gets the String of file address of the file to load from the data.transpool.user sends it to the engine.
     */
    @Override
    public void run() {
        show();
        boolean isValidInput;
        String fileName;

        do {
            try {
                fileName = getStringFromUser("Enter your file address: ");
                validateFileType(fileName);

                System.out.println("Loading " + fileName + " to system...");
                engine = new FileEngine(fileName);
                engine.loadFile();

                System.out.println("File loaded successfully!");

                isValidInput = true;
            } catch (IOException | JAXBException | TransPoolFileDataException | InvalidTimeException e) {
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

