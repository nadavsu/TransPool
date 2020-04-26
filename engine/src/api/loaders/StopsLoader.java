package api.loaders;

import api.Engine;
import data.generated.TransPool;
import data.transpool.map.Coordinates;
import data.transpool.map.Stop;

import java.util.List;
import java.util.stream.Collectors;

public class StopsLoader extends ClassLoader {
    public StopsLoader(TransPool data) {
        super(data);
    }

    public void load() {
        List<data.generated.Stop> JAXBStops = data.getMapDescriptor().getStops().getStop();
        List<Stop> parsedStops = JAXBStops
                .stream()
                .map(this::stopParser)
                .collect(Collectors.toList());

        Engine.getInstance().getTransPoolMap().setStops(parsedStops);
    }

      public data.transpool.map.Stop stopParser(data.generated.Stop JAXBStop) {
        String name = JAXBStop.getName();
        int x = JAXBStop.getX();
        int y = JAXBStop.getY();

        return new Stop(new Coordinates(x, y), name);
    }
}
