package api.transpool;

import api.transpool.map.BasicMap;
import api.transpool.time.TimeEngine;
import api.transpool.time.component.Updatable;
import api.transpool.trip.matching.MatchingEngine;
import api.transpool.trip.offer.TripOffersEngine;
import api.transpool.trip.request.TripRequestsEngine;

public interface SingleMapEngine extends Updatable, BasicMap, TripOffersEngine, TripRequestsEngine, TimeEngine, MatchingEngine {
    String getMapName();
    String getUploaderName();
    SingleMapEngineDTO getMapEngineDetails();
    BasicMap getMap();
}
