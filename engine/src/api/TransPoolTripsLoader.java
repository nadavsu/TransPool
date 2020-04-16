package api;

import jaxb.generated.TransPool;

public class TransPoolTripsLoader extends ClassLoader {
    public TransPoolTripsLoader(TransPool data) {
        super(data);
        classesToLoad.add(new RouteLoader(data));
        classesToLoad.add(new SchedulingLoader(data));
    }
}
