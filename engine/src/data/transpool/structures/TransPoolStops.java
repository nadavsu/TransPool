package data.transpool.structures;

import data.jaxb.MapDescriptor;
import data.jaxb.Stop;
import data.transpool.map.TransPoolStop;
import exceptions.file.StopNameDuplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransPoolStops {

    private Map<String, TransPoolStop> stops = new HashMap<>();

    public TransPoolStops(MapDescriptor JAXBMap) throws StopNameDuplicationException {
        List<Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for(Stop stop : JAXBStopsList) {
            if (stops.containsKey(stop.getName())) {
                throw new StopNameDuplicationException(stop.getName());
            }
            stops.put(stop.getName(), new TransPoolStop(stop));
        }
    }

    public Map<String, TransPoolStop> getStops() {
        return stops;
    }

    public boolean containsName(String name) {
        return stops.containsKey(name);
    }
}
