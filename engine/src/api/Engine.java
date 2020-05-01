package api;

import data.jaxb.TransPool;
import data.transpool.Time;
import data.transpool.TransPoolData;
import data.transpool.TransPoolTripRequest;
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
                                              int hour, int min, boolean isContinuous) throws InvalidTimeException, StopNotFoundException {
        if (!data
                .getMap()
                .getStops()
                .containsKey(source)) {
            throw new StopNotFoundException(source);
        }
        if (data
                .getMap()
                .getStops()
                .containsKey(destination)) {
            throw new StopNotFoundException(destination);
        }
        Time time = new Time(hour, min);
        data.addTransPoolTripRequest(new TransPoolTripRequest(riderName, source, destination, time, isContinuous));
    }

    public void showAllTransPoolTrips() {
        data.getTranspoolTrips().forEach(System.out::println);
    }

    public void showAllTransPoolTripRequests() {
        data.getTranspoolTripRequests().forEach(System.out::println);
    }

}
