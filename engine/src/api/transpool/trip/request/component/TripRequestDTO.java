package api.transpool.trip.request.component;

public class TripRequestDTO {
    private int requestID;
    private String riderName;
    private String sourceStopName;
    private String destinationStopName;
    private String requestTime;

    public TripRequestDTO(TripRequest tripRequest) {
        this.requestID = tripRequest.getID();
        this.riderName = tripRequest.getTransPoolRider().getUsername();
        this.sourceStopName = tripRequest.getSourceStop().getName();
        this.destinationStopName = tripRequest.getDestinationStop().getName();
        this.requestTime = tripRequest.getRequestTime().toString();
    }

    public int getRequestID() {
        return requestID;
    }

    public String getRiderName() {
        return riderName;
    }

    public String getSourceStopName() {
        return sourceStopName;
    }

    public String getDestinationStopName() {
        return destinationStopName;
    }

    public String getRequestTime() {
        return requestTime;
    }

    @Override
    public String toString() {
        return "Trip from " + sourceStopName + " to " + destinationStopName + " on " + requestTime;
    }
}
