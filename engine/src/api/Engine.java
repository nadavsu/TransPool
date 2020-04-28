package api;

import data.jaxb.TransPool;
import data.transpool.TransPoolData;
import data.transpool.map.Stop;
import data.transpool.trips.Time;
import data.transpool.trips.TripRequest;
import data.transpool.user.Rider;
import exceptions.data.PathDoesNotExistException;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;
import exceptions.file.TransPoolFileNotFoundException;
import exceptions.file.UnsupportedFileException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Engine {
    public static final String JAXB_XML_PACKAGE_NAME = "data.jaxb";

    private static Engine engine = new Engine();
    private TransPoolData data;

    private Engine() {
        data = new TransPoolData();
    }

    public static synchronized Engine getInstance() {
        if (engine == null) {
            engine = new Engine();
        }
        return engine;
    }

    public TransPoolData getData() {
        return data;
    }

    public void loadFile(String fileName) throws JAXBException, TransPoolFileNotFoundException, TransPoolDataException {
        InputStream istream =  Engine.class.getResourceAsStream("/resources/" + fileName);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);
        data = new TransPoolData(JAXBData);
    }

    public void createNewTripRequest(String riderName, String sourceName, String destinationName, int hour, int min)
            throws StopNotFoundException, InvalidTimeException {

            Stop sourceStop = getStop(sourceName);
            Stop destinationStop = getStop(destinationName);
            addRequest(new TripRequest(new Rider(riderName), sourceStop, destinationStop, new Time(hour, min)));
    }

    public void showAllTransPoolTrips() {
        data.getTranspoolTrips().forEach(System.out::println);
    }

    public void showAllTripRequests() {
        data.getTripRequests().forEach(System.out::println);
    }

    private void addRequest(TripRequest newRequest) {
        data.addRequest(newRequest);
    }

    private Stop getStop(String stopName) throws StopNotFoundException {
        return data.getStop(stopName);
    }
}
