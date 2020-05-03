package data.transpool.structures;

import data.jaxb.MapDescriptor;
import data.transpool.map.Map;
import data.transpool.map.Path;
import exceptions.file.PathDoesNotExistException;
import exceptions.file.PathDuplicationException;
import exceptions.file.TransPoolFileDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TransPoolPaths {
    private List<Path> paths = new ArrayList<>();

    public TransPoolPaths(MapDescriptor JAXBMap) throws TransPoolFileDataException {
        List<data.jaxb.Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (data.jaxb.Path JAXBPath : JAXBPathList) {
            Path transpoolPath = new Path(JAXBPath);
            String source = JAXBPath.getFrom();
            String destination = JAXBPath.getTo();
            if (!Map.getAllStops().containsName(source)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (!Map.getAllStops().containsName(destination)) {
                throw new PathDoesNotExistException(source, destination);
            }
            if (paths.contains(transpoolPath)) {
                throw new PathDuplicationException(source, destination);
            }
            if (!JAXBPath.isOneWay()) {
                Path swappedPath = new Path(transpoolPath);
                swappedPath.swapDirection();
                paths.add(swappedPath);
            }
            paths.add(transpoolPath);
        }

    }

    public Path getPathBySourceAndDestination(String source, String destination) throws PathDoesNotExistException {
        Predicate<Path> sourceDestinationMatchPredicate = p ->
                p.getDestination().equals(destination) && p.getSource().equals(source);

        Path foundPath = paths
                .stream()
                .filter(sourceDestinationMatchPredicate)
                .findFirst()
                .orElse(null);

        if (foundPath == null) {
            throw new PathDoesNotExistException(source, destination);
        }
        return foundPath;
    }
}
