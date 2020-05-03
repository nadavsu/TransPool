package data.transpool.structures;

import data.jaxb.MapDescriptor;
import data.jaxb.Path;
import data.transpool.map.TransPoolPath;
import exceptions.file.PathDoesNotExistException;
import exceptions.file.PathDuplicationException;
import exceptions.file.TransPoolFileDataException;

import java.util.ArrayList;
import java.util.List;

public class TransPoolPaths {
    private List<TransPoolPath> paths = new ArrayList<>();

    public TransPoolPaths(MapDescriptor JAXBMap) throws TransPoolFileDataException {
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

    public TransPoolPath getPathBySourceAndDestination(String source, String destination) throws PathDoesNotExistException {
        for (TransPoolPath path : paths) {
            if (path.getSource().equals(source)
                    && path.getDestination().equals(destination)) {
                return path;
            }
        }
        throw new PathDoesNotExistException(source, destination);
    }
}
