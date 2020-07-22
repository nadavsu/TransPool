package data.transpool;

import data.transpool.map.BasicMap;
import data.transpool.time.TimeEngine;
import data.transpool.trip.offer.TripOfferEngine;
import data.transpool.trip.request.TripRequestEngine;
import data.transpool.user.UserEngine;

public interface TransPoolMap extends Updatable {
    String getMapName();
    String getUploaderName();
    BasicMap getMap();
    TripRequestEngine getTripRequestEngine();
    TripOfferEngine getTripOfferEngine();
    TimeEngine getTimeEngine();
}
