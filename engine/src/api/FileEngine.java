package api;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import exceptions.file.TransPoolFileNotFoundException;
import exceptions.file.TransPoolDataFileException;
import exceptions.time.InvalidTimeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * This engine handles the TransPool data file.
 * TODO: check if it works with a directory on your computer.
 */
public class FileEngine extends Engine {

    private static final String JAXB_XML_PACKAGE_NAME = "data.jaxb";
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
     * @throws TransPoolDataFileException - If there's a problem with the TransPool data file.
     * @throws InvalidTimeException - If the time in the TP data file is invalid.
     */
    public void loadFile() throws JAXBException, TransPoolFileNotFoundException,
            TransPoolDataFileException, InvalidTimeException {
        InputStream istream = Engine.class.getResourceAsStream(fileDirectory);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);
        data = new TransPoolData(JAXBData);
    }
}
