package api;

import exceptions.file.TransPoolFileNotFoundException;
import jaxb.generated.*;
import map.Map;
import trips.TranspoolTrip;
import trips.TripRequest;
import validators.Constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Engine {

    private static volatile Engine engine = new Engine();

    private static volatile Map transpoolMap;
    private static volatile List<TripRequest> tripRequests;
    private static volatile List<TranspoolTrip> transpoolTrips;

    private Engine() {

    }

    public static Engine getInstance() {
        if (engine != null) {
            engine = new Engine();
        }
        return engine;
    }

    public Map getTransPoolMap() {
        return transpoolMap;
    }

    public void setTransPoolMap(Map transpoolMap) {
        Engine.transpoolMap = transpoolMap;
    }

    public static List<TripRequest> getTripRequests() {
        return tripRequests;
    }

    public static void setTripRequests(List<TripRequest> tripRequests) {
        Engine.tripRequests = tripRequests;
    }

    public static List<TranspoolTrip> getTransPoolTrips() {
        return transpoolTrips;
    }

    public static void setTransPoolTrips(List<TranspoolTrip> transpoolTrips) {
        Engine.transpoolTrips = transpoolTrips;
    }

    //---------------------------------------------------------------------------------------------

    public void loadFile(String fileAddress) throws FileNotFoundException {
        try {
            File fileToLoad = new File(fileAddress);
            if (!fileToLoad.exists()) {
                throw new TransPoolFileNotFoundException();
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(Constants.JAXB_XML_PACKAGE_NAME);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            TransPool JAXBData = (TransPool) unmarshaller.unmarshal(fileToLoad);
            Loader loader = new TransPoolLoader(JAXBData);
            loader.load();
            //this.new ClassLoader().loadClassesFromJAXBGenerated(JAXBData);

        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        }

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
