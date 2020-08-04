package api.transpool.trip.matching;

import api.transpool.trip.matching.component.PossibleRoutesList;

public interface MatchingEngine {
    PossibleRoutesList getAllPossibleRoutes(int tripRequestID, int maximumMatches);
}
