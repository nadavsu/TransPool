package data.transpool.structure;

import data.jaxb.MapDescriptor;
import data.transpool.map.Stop;
import exception.file.StopNameDuplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransPoolStops {

    private Map<String, Stop> stops = new HashMap<>();

    public TransPoolStops(MapDescriptor JAXBMap) throws StopNameDuplicationException {
        List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for(data.jaxb.Stop stop : JAXBStopsList) {
            if (stops.containsKey(stop.getName())) {
                throw new StopNameDuplicationException(stop.getName());
            }
            stops.put(stop.getName(), new Stop(stop));
        }
    }

    public Map<String, Stop> getStops() {
        return stops;
    }

    public boolean containsName(String name) {
        return stops.containsKey(name);
    }
}
