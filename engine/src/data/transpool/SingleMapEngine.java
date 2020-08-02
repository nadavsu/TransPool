package data.transpool;

import data.transpool.map.BasicMap;
import data.transpool.time.TimeEngine;
import data.transpool.trip.matching.MatchingEngine;
import data.transpool.trip.offer.TripOffersEngine;
import data.transpool.trip.request.TripRequestsEngine;

public interface SingleMapEngine extends Updatable, BasicMap, TripOffersEngine, TripRequestsEngine, TimeEngine, MatchingEngine {
    String getMapName();
    String getUploaderName();
    SingleMapEngineDTO getMapEngineDetails();
    BasicMap getMap();
}
