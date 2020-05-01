package data.transpool;

public class TransPoolTripRequest {
    private String riderName;
    private String source;
    private String destination;
    private Time timeOfDeparture;
    private boolean isContinuous;

    public TransPoolTripRequest(String riderName, String source, String destination, Time timeOfDeparture, boolean isContinuous) {
        this.riderName = riderName;
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.isContinuous = isContinuous;
    }

    public String getRiderName() {
        return riderName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Time getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public boolean isContinuous() {
        return isContinuous;
    }
}
