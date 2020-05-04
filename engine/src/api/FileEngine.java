package api;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import exceptions.file.TransPoolFileNotFoundException;
import exceptions.file.TransPoolDataException;
import exceptions.time.InvalidTimeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

/**
 * This engine handles the TransPool data file.
 */
public class FileEngine extends Engine {

    private String fileDirectory;

    public FileEngine(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    /**
     * Loads the TransPool data file to the system.
     * Deserializes the XML file and unmarshalls it to the generated JAXB classes.
     * The engine then creates a new data instance using the JAXB generated TransPool classes.
     * @throws JAXBException - If there is a problem with the JAXB files.
     * @throws TransPoolFileNotFoundException - If the file at file address was not found.
     * @throws TransPoolDataException - If there's a problem with the TransPool data file.
     */
    public void loadFile() throws JAXBException, TransPoolFileNotFoundException,
            TransPoolDataException {

        File file = new File(fileDirectory);

        if (!file.exists()) {
            throw new TransPoolFileNotFoundException();
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(file);
        data = new TransPoolData(JAXBData);
    }
}
