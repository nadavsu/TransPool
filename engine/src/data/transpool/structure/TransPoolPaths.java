package data.transpool.structure;

import data.jaxb.MapDescriptor;
import data.transpool.map.Map;
import data.transpool.map.Path;
import exception.file.PathDoesNotExistException;
import exception.file.PathDuplicationException;
import exception.file.TransPoolDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A structure containing the list of paths.
 */
public class TransPoolPaths {
    private List<Path> paths = new ArrayList<>();

    /**
     * Constructor for creating a list of paths from JAXB generated path classes.
     * @param JAXBMap - The JAXB generated path.
     * @throws TransPoolDataException - thrown if there's a problem with the data inside the JAXB classes/file.
     */
    public TransPoolPaths(MapDescriptor JAXBMap) throws TransPoolDataException {
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

    /**
     * Finds the path with the given source and destination stop names.
     * @param source - The name of the source stop.
     * @param destination - The name of the destination stop.
     * @return A reference to the path from the list of all paths if found, null otherwise.
     */
    public Path getPathBySourceAndDestination(String source, String destination) {
        Predicate<Path> sourceDestinationMatchPredicate = p ->
                p.getDestination().equals(destination) && p.getSource().equals(source);

        return paths
                .stream()
                .filter(sourceDestinationMatchPredicate)
                .findFirst()
                .orElse(null);
    }

    public boolean containsPath(String source, String destination) {
        return getPathBySourceAndDestination(source, destination) != null;
    }
}
