package api.transpool.trip.matching.component;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Unfinished class - an arrayList of PossibleRoutes which in the future could be sorted in different ways.
 */
public class PossibleRoutesList extends ArrayList<PossibleRoute>{

    public List<PossibleRouteDTO> getDetails() {
        return stream()
                .map(PossibleRoute::getDetails)
                .collect(Collectors.toList());
    }

    public PossibleRoutesList sortLeastChangesFirst() {
        return this.stream()
                .sorted(Comparator.comparingInt(PossibleRoute::getTotalPrice))
                .collect(Collectors.toCollection(PossibleRoutesList::new));
    }

    public PossibleRoutesList sortFastestFirst() {
        return this.stream()
                .sorted(Comparator.comparing(PossibleRoute::getArrivalTime))
                .collect(Collectors.toCollection(PossibleRoutesList::new));
    }

    public PossibleRoutesList sortGreenestFist() {
        return this.stream()
                .sorted(Comparator.comparingDouble(PossibleRoute::getAverageFuelConsumption))
                .collect(Collectors.toCollection(PossibleRoutesList::new));
    }

    public PossibleRoute getCheapest() {
        return this
                .stream()
                .min(Comparator.comparingInt(PossibleRoute::getTotalPrice))
                .get();
    }

    public PossibleRoute getFastest() {
        return this
                .stream()
                .min(Comparator.comparing(PossibleRoute::getArrivalTime))
                .get();
    }

    public PossibleRoute getGreenest() {
        return this
                .stream()
                .min(Comparator.comparingDouble(PossibleRoute::getAverageFuelConsumption))
                .get();
    }

    public PossibleRoute getLeastChanges() {
        return null;
    }
}
