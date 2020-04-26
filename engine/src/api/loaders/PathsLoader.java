package api.loaders;

import api.Engine;
import data.generated.TransPool;
import data.transpool.map.Path;

import java.util.List;
import java.util.stream.Collectors;

public class PathsLoader extends ClassLoader {
    public PathsLoader(TransPool data) {
        super(data);
    }

    public void load() {
        List<data.generated.Path> JAXBPath = data.getMapDescriptor().getPaths().getPath();
        List<data.transpool.map.Path> parsedMaps = JAXBPath
                .stream()
                .map(this::pathParser)
                .collect(Collectors.toList());

        Engine.getInstance().getTransPoolMap().setPaths(parsedMaps);
    }

    private data.transpool.map.Path pathParser(data.generated.Path JAXBPath) {
        int length = JAXBPath.getLength();
        int fuelConsumption = JAXBPath.getFuelConsumption();
        int speedLimit = JAXBPath.getSpeedLimit();
        boolean isOneWay = JAXBPath.isOneWay();
        String source = JAXBPath.getFrom();
        String destination = JAXBPath.getTo();

        return new Path(length, fuelConsumption, speedLimit, isOneWay, source, destination);
    }
}
