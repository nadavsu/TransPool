package api;

import data.generated.TransPool;
import data.transpool.TransPoolData;
import exceptions.file.TransPoolFileNotFoundException;
import data.transpool.map.Map;
import data.transpool.trips.TransPoolTrip;
import data.transpool.trips.TripRequest;
import validators.Constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

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
        data.generated.TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);
        data = new TransPoolData(JAXBData);
    }

    public void createNewTripRequest(String stopSource, String stopDestination, int hour, int min) {

        //create a stop for destination
        //create a stop for source
        //create a new trip request using the constructor
        //add it to the list here.
    }

    //Todo: toString for transpool trip.
    public void showAllTranspoolTrips() {
        data.getTranspoolTrips().forEach(System.out::println);
    }

    //Todo: toString for trip requests.
    public void showAllTripRequests() {
        data.getTripRequests().forEach(System.out::println);
    }

}
