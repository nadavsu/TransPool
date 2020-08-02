package data.transpool.trip.matching;

import data.transpool.trip.matching.component.PossibleRoutesList;

public interface MatchingEngine {
    PossibleRoutesList getAllPossibleRoutes(int tripRequestID, int maximumMatches);
}
