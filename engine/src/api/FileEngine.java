package api;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import exceptions.file.TransPoolFileNotFoundException;
import exceptions.file.TransPoolFileDataException;
import exceptions.time.InvalidTimeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class FileEngine extends Engine {

    private static final String JAXB_XML_PACKAGE_NAME = "data.jaxb";
    private String fileDirectory;

    public FileEngine(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public void loadFile() throws JAXBException, TransPoolFileNotFoundException,
            TransPoolFileDataException, InvalidTimeException {
        InputStream istream = Engine.class.getResourceAsStream("/resources/" + fileDirectory);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);
        data = new TransPoolData(JAXBData);
    }
}
