package data.transpool.map.trip.offer;

import data.jaxb.TransPoolTrip;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.offer.TripOfferData;
import data.transpool.util.WeightedGraph;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.List;

public class TripOfferMapData implements TripOfferMap {
    private ObservableList<TripOffer> allTripOffers;
    private WeightedGraph<Stop, Path, TripOffer> tripOfferGraph;

    public TripOfferMapData(BasicMap map, List<TransPoolTrip> JAXBTripOffers) throws TransPoolDataException {
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
        tripOffer
                .getRoute()
                .getUsedPaths()
                .forEach(path -> {
                    newConnection(path, tripOffer);
                });
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return allTripOffers;
    }

    //----------------------------------------------------------------------------------------------------------------//

    @Override
    public void newConnection(Path path, TripOffer tripOffer) {
        tripOfferGraph.newConnection(path, tripOffer);
    }

    @Override
    public List<List<Pair<Stop, TripOffer>>> getAllPossibleRoutes(Stop source, Stop destination) {
        return tripOfferGraph.getAllPossibleRoutes(source, destination);
    }

    @Override
    public List<List<Pair<Stop, TripOffer>>> getGraph() {
        return tripOfferGraph.getGraph();
    }
}
