package data.transpool.trips;

import data.transpool.user.TransPoolRider;

import java.util.Objects;

public class TransPoolTripRequest {
    private static int IDGenerator = 20000;
    private int ID;
    private TransPoolRider transpoolRider;
    private String source;
    private String destination;
    private Time timeOfDeparture;

    private boolean isContinuous;
    private boolean isMatched;

    public TransPoolTripRequest(String transpoolRider, String source, String destination, Time timeOfDeparture, boolean isContinuous) {
        this.ID = IDGenerator++;
        this.transpoolRider = new TransPoolRider(transpoolRider);
        this.source = source;
        this.destination = destination;
        this.timeOfDeparture = timeOfDeparture;
        this.isContinuous = isContinuous;
        this.isMatched = false;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public TransPoolRider getTranspoolRider() {
        return (TransPoolRider) transpoolRider;
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

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String requestString = "";
        requestString += "------TransPool Trip Request:------\n";
        requestString += "Name of requester: " + transpoolRider + "\n";
        requestString += "Stop source: " + source + "\n";
        requestString += "Stop destination: " + destination + "\n";
        requestString += "Time of departure: " + timeOfDeparture + "\n";

        return requestString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransPoolTripRequest)) return false;
        TransPoolTripRequest that = (TransPoolTripRequest) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
