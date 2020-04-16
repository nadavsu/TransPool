package api;

import jaxb.generated.TransPool;

public class MapBoundariesLoader extends ClassLoader {
    public MapBoundariesLoader(TransPool data) {
        super(data);
    }

    public void load() {
        int mapWidth = data
                .getMapDescriptor()
                .getMapBoundries()
                .getWidth();
        int mapLength = data
                .getMapDescriptor()
                .getMapBoundries()
                .getLength();

        Engine.getInstance()
                .getTransPoolMap()
                .setLength(mapLength);
        Engine.getInstance()
                .getTransPoolMap()
                .setWidth(mapWidth);
    }
}
