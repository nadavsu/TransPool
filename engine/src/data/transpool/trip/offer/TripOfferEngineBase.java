package data.transpool.trip.offer;

import data.generated.TransPoolTrip;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Stop;
import data.transpool.time.component.TimeDay;
import data.transpool.trip.offer.component.SubTripOffer;
import data.transpool.trip.offer.component.TripOffer;
import data.transpool.trip.offer.component.TripOfferData;
import data.transpool.trip.offer.graph.TripOfferGraph;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.request.component.TripRequest;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The main class which holds the trip offer graph and all the trip offers data. Also holds the timed data
 * such as current trip offers and current sub trip offers which are happening.
 */
public class TripOfferEngineBase implements TripOfferEngine {
    private ObservableList<TripOffer> allTripOffers;
    private TripOfferGraph tripOfferGraph;

    //Live details
    private ObservableList<TripOffer> currentTripOffers;
    private List<SubTripOffer> currentSubTripOffers;


    public TripOfferEngineBase(BasicMap map, List<TransPoolTrip> JAXBTripOffers) throws TransPoolDataException {
        this.allTripOffers = FXCollections.observableArrayList();
        this.currentSubTripOffers = new ArrayList<>();
        this.currentTripOffers = FXCollections.observableArrayList();
        initAllTripOffers(map, JAXBTripOffers);
        update();

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
    public List<SubTripOffer> getCurrentSubTripOffers() {
        return currentSubTripOffers;
    }

    @Override
    public SubTripOffer getSubTripOffer(int tripOfferID, int subTripOfferID) {
        return getTripOffer(tripOfferID).getSubTripOffer(subTripOfferID);
    }

    //----------------------------------------------------------------------------------------------------------------//

    /**
     * Gets the possible routes from the TripOfferMap, and filters all routes which are not relevant by
     * departure or arrival time. Also filters all rides that are not continuous if the rider asked for continuous rides.
     * @param tripRequest
     * @param maximumMatches
     * @return
     */
    @Override
    public PossibleRoutesList getAllPossibleRoutes(TripRequest tripRequest, int maximumMatches) {

        Predicate<PossibleRoute> timeMatchPredicate = possibleRoute -> {
            if (tripRequest.isTimeOfArrival()) {
                return possibleRoute.getTimeOfArrival().equals(tripRequest.getRequestTime());
            } else {
                return possibleRoute.getTimeOfDeparture().equals(tripRequest.getRequestTime());
            }
        };

        Predicate<PossibleRoute> continuousRidePredicate = possibleRoute ->
                !tripRequest.isContinuous() || possibleRoute.isContinuous();

        PossibleRoutesList possibleRoutes = tripOfferGraph.getAllPossibleRoutes(
                tripRequest.getSourceStop(), tripRequest.getDestinationStop(), tripRequest.getRequestTime());

        return possibleRoutes.stream()
                .filter(timeMatchPredicate)
                .filter(continuousRidePredicate)
                .limit(maximumMatches)
                .collect(Collectors.toCollection(PossibleRoutesList::new));
    }

    /**
     * This function updates the map every time the system's time is changed.
     * 1. Gets all the current tripoffers happening.
     * 2. Populates the list of current subTripOffers happening
     * 3. Updates the stops with the relevant details through the subTripOffers using subTripOffer.currentDetails() functions.
     *    The details are shown on the live map when a stop is clicked.
     */
    @Override
    public void update() {
        currentTripOffers.clear();
        currentSubTripOffers.clear();
        for (TripOffer tripOffer : allTripOffers) {
            if (tripOffer.isCurrentlyHappening()) {
                currentTripOffers.add(tripOffer);
                currentSubTripOffers.add(tripOffer.getCurrentSubTripOffer());
            }
        }
        for (SubTripOffer subTripOffer : currentSubTripOffers) {
            if (subTripOffer != null && subTripOffer.isCurrentlyDeparting()) {
                //subTripOffer.getSourceStop().addDetails(subTripOffer.currentDetails());
            } else if (subTripOffer != null && subTripOffer.isCurrentlyArriving()) {
                //subTripOffer.getDestinationStop().addDetails(subTripOffer.currentDetails());
            }
        }
    }

    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, TimeDay departureTime) {
        return tripOfferGraph.getAllPossibleRoutes(source, destination, departureTime);
    }

}
