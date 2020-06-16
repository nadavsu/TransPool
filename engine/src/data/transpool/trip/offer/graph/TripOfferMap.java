package data.transpool.trip.offer.graph;

import api.components.TripOfferEngine;
import data.jaxb.TransPoolTrip;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.data.TripOfferData;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.request.MatchedTripRequest;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripOfferMap implements TripOfferEngine {
    private ObservableList<TripOffer> allTripOffers;
    private ObservableList<TripOffer> currentTripOffers;
    private TripOfferGraph tripOfferGraph;

    public TripOfferMap(BasicMap map, List<TransPoolTrip> JAXBTripOffers) throws TransPoolDataException {
        this.allTripOffers = FXCollections.observableArrayList();

        initAllTripOffers(map, JAXBTripOffers);
        this.tripOfferGraph = new TripOfferGraph(map.getNumberOfStops(), allTripOffers);
    }

    private void initAllTripOffers(BasicMap map, List<TransPoolTrip> JAXBTripOffers) throws TransPoolDataException {
        for (TransPoolTrip JAXBTrip : JAXBTripOffers) {
            allTripOffers.add(new TripOfferData(JAXBTrip, map));
        }
    }

    @Override
    public TripOffer getTripOffer(int ID) {
        return allTripOffers
                .stream()
                .filter(t -> t.getOfferID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addTripOffer(TripOffer tripOffer) {
        allTripOffers.add(tripOffer);
        tripOfferGraph.add(tripOffer);
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return allTripOffers;
    }

    @Override
    public ObservableList<TripOffer> getCurrentOffers() {
        return currentTripOffers;
    }

    @Override
    public void updateAfterMatch(PossibleRoute chosenPossibleRoute, MatchedTripRequest matchedTripRequest) {
        for (SubTripOffer subTripOffer : chosenPossibleRoute.getRoute()) {
            getSubTripOffer(
                    subTripOffer.getOfferID(),
                    subTripOffer.getSubTripOfferID()
            )
                    .updateOnDay(subTripOffer.getDayStart());
        }
    }

    //----------------------------------------------------------------------------------------------------------------//

    public void updateCurrentTripOffers() {
        currentTripOffers.clear();
        allTripOffers
                .stream()
                .filter(TripOffer::isCurrentlyHappening)
                .forEach(tripOffer -> currentTripOffers.add(tripOffer));
    }

    public SubTripOffer getSubTripOffer(int tripOfferID, int subTripOfferID) {
        return getTripOffer(tripOfferID).getSubTripOffer(subTripOfferID);
    }

    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, TimeDay departureTime) {
        return tripOfferGraph.getAllPossibleRoutes(source, destination, departureTime);
    }

    public TripOfferGraph getTripOfferGraph() {
        return tripOfferGraph;
    }

}
