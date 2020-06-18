package api.task;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import exception.data.TransPoolDataException;
import exception.data.TransPoolFileNotFoundException;
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
    protected TransPoolData call() {
        try {
            if (!fileToLoad.exists()) {
                throw new TransPoolFileNotFoundException();
            }

            Thread.sleep(300);
            updateMessage("Fetching file...");
            JAXBContext jaxbContext = JAXBContext.newInstance(TransPool.class);
            Thread.sleep(300);
            updateMessage("Loading file to system...");
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TransPool JAXBData = (TransPool) jaxbUnmarshaller.unmarshal(fileToLoad);
            Thread.sleep(300);
            updateMessage("Parsing...");

            TransPoolData data = new TransPoolData(JAXBData);
            updateMessage("File loaded successfully!");
            return data;
        } catch (TransPoolDataException | TransPoolFileNotFoundException e) {
            updateMessage(e.getMessage());
            return null;
        } catch (JAXBException e) {
            updateMessage("There was an internal problem (JAXB Error).");
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

}
