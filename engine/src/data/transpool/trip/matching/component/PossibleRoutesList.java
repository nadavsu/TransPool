package data.transpool.trip.matching.component;

import java.util.ArrayList;
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

    public void sortCheapestFirst() {

    }

    public void sortLeastChangesFirst() {

    }

    public void sortFastestFirst() {

    }

    public void sortGreenestFist() {

    }

    public PossibleRoute getCheapest() {
        return null;
    }

    public PossibleRoute getFastest() {
        return null;
    }

    public PossibleRoute getGreenest() {
        return null;
    }

    public PossibleRoute getLeastChanges() {
        return null;
    }
}
