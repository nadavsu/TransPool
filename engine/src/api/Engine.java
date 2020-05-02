package api;

import data.jaxb.TransPool;
import data.transpool.Time;
import data.transpool.TransPoolData;
import data.transpool.TransPoolTripRequest;
import data.transpool.structures.TransPoolMap;
import data.transpool.structures.TransPoolTripRequests;
import data.transpool.structures.TransPoolTrips;
import exceptions.data.StopNotFoundException;
import exceptions.data.TransPoolDataException;
import exceptions.data.time.InvalidTimeException;
import exceptions.file.TransPoolFileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
public class Engine {

    private static TransPoolData data;
    private static final String JAXB_XML_PACKAGE_NAME = "data.jaxb";

    public Engine() {
    }

    public void loadFile(String fileName) throws JAXBException, TransPoolFileNotFoundException,
            TransPoolDataException, InvalidTimeException {
        InputStream istream =  Engine.class.getResourceAsStream("/resources/" + fileName);
        if (istream == null) {
            throw new TransPoolFileNotFoundException();
        }
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        TransPool JAXBData = (TransPool) unmarshaller.unmarshal(istream);
        data = new TransPoolData(JAXBData);

    }

    public TransPoolData getData() {
        return data;
    }

    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              int hour, int min, boolean isContinuous) throws InvalidTimeException,
                                              StopNotFoundException {
        if (!TransPoolMap.getAllStops().containsName(source)) {
            throw new StopNotFoundException(source);
        }
        if (!TransPoolMap.getAllStops().containsName(destination)) {
            throw new StopNotFoundException(destination);
        }
        TransPoolData
                .getAllTransPoolTripRequests()
                .addTransPoolTripRequest(new TransPoolTripRequest(riderName, source, destination,
                                         new Time(hour, min), isContinuous));
    }

    public TransPoolTrips getAllTransPoolTrips() {
        return TransPoolData.getAllTransPoolTrips();
    }

    public TransPoolTripRequests getAllTransPoolTripRequests() {
        return TransPoolData.getAllTransPoolTripRequests();
    }

    public void matchTrip(int tripID) {

    }

    public void _debugfill() throws InvalidTimeException, StopNotFoundException {
        createNewTransPoolTripRequest("Nadav", "MTA", "Bursa", 17, 0, true);
        createNewTransPoolTripRequest("Lasri", "Park Hayarkon", "Wolfson train", 6, 0, true);
        createNewTransPoolTripRequest("Shaide", "Lagardia", "HIT", 22, 0, true);
        createNewTransPoolTripRequest("Rami", "HIT", "MTA", 23, 0, true);
    }
}
