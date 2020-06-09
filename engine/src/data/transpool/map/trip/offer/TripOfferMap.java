package data.transpool.map.trip.offer;

import api.components.TripOfferEngine;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.TripOffer;
import data.transpool.util.WeightedGraph;

public interface TripOfferMap extends WeightedGraph<Stop, Path, TripOffer>, TripOfferEngine {

}
