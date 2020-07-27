package data.transpool.trip.request.component;

import data.transpool.time.component.TimeDay;

public class TripRequestDTO {
    private String riderName;
    private String sourceStopName;
    private String destinationStopName;
    private TimeDay requestTime;

    public TripRequestDTO(TripRequest tripRequest) {
        this.riderName = tripRequest.getTransPoolRider().getUsername();
        this.sourceStopName = tripRequest.getSourceStop().getName();
        this.destinationStopName = tripRequest.getDestinationStop().getName();
        this.requestTime = tripRequest.getRequestTime();
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

    public TimeDay getRequestTime() {
        return requestTime;
    }
}
