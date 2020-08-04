package api.transpool;

public class SingleMapEngineDTO {

    private String mapName;
    private String uploaderName;

    private int numOfTripOffers;
    private int numOfTripRequests;
    private int numOfMatchedRequests;
    private int numOfStops;
    private int numOfPaths;

    public SingleMapEngineDTO(SingleMapEngine transpoolMap) {
        this.mapName = transpoolMap.getMapName();
        this.uploaderName = transpoolMap.getUploaderName();
        this.numOfTripOffers = transpoolMap.getNumOfTripOffers();
        this.numOfTripRequests = transpoolMap.getNumOfTripRequests();
        this.numOfMatchedRequests = transpoolMap.getNumOfMatchedRequests();
        this.numOfStops = transpoolMap.getNumberOfStops();
        this.numOfPaths = transpoolMap.getNumberOfPaths();
    }

    public String getMapName() {
        return mapName;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public int getNumOfTripOffers() {
        return numOfTripOffers;
    }

    public int getNumOfTripRequests() {
        return numOfTripRequests;
    }

    public int getNumOfMatchedRequests() {
        return numOfMatchedRequests;
    }

    public int getNumOfStops() {
        return numOfStops;
    }

    public int getNumOfPaths() {
        return numOfPaths;
    }
}
