package data.transpool.structures;

import data.jaxb.MapDescriptor;
import data.jaxb.Path;
import data.transpool.TransPoolData;
import data.transpool.TransPoolPath;
import exceptions.data.PathDoesNotExistException;
import exceptions.data.PathDuplicationException;
import exceptions.data.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolPaths {
    private List<TransPoolPath> paths = new ArrayList<>();

    public TransPoolPaths(MapDescriptor JAXBMap) throws TransPoolDataException {
        List<Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (Path JAXBPath : JAXBPathList) {
            TransPoolPath transpoolPath = new TransPoolPath(JAXBPath);
            String source = JAXBPath.getFrom();
            String destination = JAXBPath.getTo();
            if (!TransPoolMap.getAllStops().containsName(source)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (!TransPoolMap.getAllStops().containsName(destination)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (paths.contains(transpoolPath)) {
                throw new PathDuplicationException(source, destination);
            }
            if (!JAXBPath.isOneWay()) {
                TransPoolPath swappedPath = new TransPoolPath(transpoolPath);
                swappedPath.swapDirection();
                paths.add(swappedPath);
            }
            paths.add(transpoolPath);
        }

    }

    public TransPoolPath getPathBySourceAndDestination(String source, String destination) {
        return paths
                .stream()
                .filter(p -> p.getSource().equals(source) &&
                        p.getDestination().equals(destination))
                .findAny()
                .orElse(null);
    }
}
