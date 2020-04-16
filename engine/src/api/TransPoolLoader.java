package api;

import jaxb.generated.TransPool;

public class TransPoolLoader extends ClassLoader {

    public TransPoolLoader(TransPool data) {
        super(data);
        classesToLoad.add(new TransPoolTripsLoader(data));
        classesToLoad.add(new MapDescriptorLoader(data));
    }

}
