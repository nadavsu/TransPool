package api.task;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import exception.file.TransPoolDataException;
import exception.file.TransPoolFileNotFoundException;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class LoadFileTask extends Task<TransPoolData> {

    private File fileToLoad;

    public LoadFileTask(File fileToLoad) {
        this.fileToLoad = fileToLoad;
    }

    @Override
    protected TransPoolData call() throws TransPoolFileNotFoundException, JAXBException, TransPoolDataException {
        if (!fileToLoad.exists()) {
            throw new TransPoolFileNotFoundException();
        }
        updateMessage("Fetching file...");
        JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);

        updateMessage("Loading file to system...");
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(fileToLoad);

        updateMessage("Parsing...");
        TransPoolData data = new TransPoolData(JAXBData);

        Task<Boolean> loadUITask = new LoadUITask(data.getAllTransPoolTrips());
        new Thread(loadUITask).start();
        //Platform.runLater(A function that updates the GUI with the cards);

        updateMessage("File loaded successfully!");
        return data;
    }

}
