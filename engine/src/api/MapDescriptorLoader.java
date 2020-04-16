package api;

import jaxb.generated.TransPool;

public class MapDescriptorLoader extends ClassLoader {
    public MapDescriptorLoader(TransPool data) {
        super(data);
        classesToLoad.add(new MapBoundariesLoader(data));
        classesToLoad.add(new PathsLoader(data));
        classesToLoad.add(new StopsLoader(data));
    }
}
