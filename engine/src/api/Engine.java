package api;

import data.generated.TransPool;
import data.transpool.TransPoolData;
import data.transpool.map.Stop;
import data.transpool.trips.Time;
import data.transpool.trips.TripRequest;
import exceptions.data.StopNotFoundException;
import exceptions.file.TransPoolFileNotFoundException;
import validators.Constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Engine {
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

    public void loadFile(String fileAddress) throws JAXBException, FileNotFoundException {
        InputStream istream =  Engine.class.getResourceAsStream("/resources/" + fileAddress);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(Constants.JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);

        data = new TransPoolData(JAXBData);
    }

    public void createNewTripRequest(String sourceName, String destinationName, int hour, int min) throws StopNotFoundException {
            Stop sourceStop = getStop(sourceName);
            Stop destinationStop = getStop(destinationName);
            addRequest(new TripRequest(sourceStop, destinationStop, new Time(hour, min)));
    }

    public void showAllTransPoolTrips() {
        data.getTranspoolTrips().forEach(System.out::println);
    }

    public void showAllTripRequests() {
        data.getTripRequests().forEach(System.out::println);
    }

    public void addRequest(TripRequest newRequest) {
        data.addRequest(newRequest);
    }

    public Stop getStop(String stopName) throws StopNotFoundException {
        return data.getStop(stopName);
    }

}
