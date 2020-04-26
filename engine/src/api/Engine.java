package api;

import data.generated.TransPool;
import api.loaders.Loader;
import api.loaders.TransPoolLoader;
import exceptions.file.TransPoolFileNotFoundException;
import data.transpool.map.Map;
import data.transpool.trips.TranspoolTrip;
import data.transpool.trips.TripRequest;
import validators.Constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    private static Engine engine = new Engine();
    private Map transpoolMap;
    private List<TripRequest> tripRequests;
    private List<TranspoolTrip> transpoolTrips;

    private Engine() {
        transpoolMap = new Map();
        tripRequests = new ArrayList<>();
        transpoolTrips = new ArrayList<>();
    }

    public static Engine getInstance() {
        if (engine == null) {
            engine = new Engine();
        }
        return engine;
    }

    public Map getTransPoolMap() {
        return transpoolMap;
    }

    public void setTransPoolMap(Map transpoolMap) {
        this.transpoolMap = transpoolMap;
    }

    public List<TripRequest> getTripRequests() {
        return tripRequests;
    }

    public void setTripRequests(List<TripRequest> tripRequests) {
        this.tripRequests = tripRequests;
    }

    public List<TranspoolTrip> getTransPoolTrips() {
        return transpoolTrips;
    }

    public void setTransPoolTrips(List<TranspoolTrip> transpoolTrips) {
        this.transpoolTrips = transpoolTrips;
    }

    //---------------------------------------------------------------------------------------------

    public void loadFile(String fileAddress) throws JAXBException, FileNotFoundException {
        InputStream istream =  Engine.class.getResourceAsStream("/resources/" + fileAddress);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(Constants.JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);

        Loader transPoolClassLoader = new TransPoolLoader(JAXBData);
        transPoolClassLoader.load();
    }

    public void createNewTripRequest(String stopSource, String stopDestination, int hour, int min) {
        //create a stop for destination
        //create a stop for source
        //create a new trip request using the constructor
        //add it to the list here.
    }

    //Todo: toString for transpool trip.
    public void showAllTranspoolTrips() {
        transpoolTrips.forEach(System.out::println);
    }

    //Todo: toString for trip requests.
    public void showAllTripRequests() {
        tripRequests.forEach(System.out::println);
    }

    public List<TranspoolTrip> getAllTranspoolTrips() {
        return transpoolTrips;
    }

    public List<TripRequest> getAllTripRequests() {
        return tripRequests;
    }

}
